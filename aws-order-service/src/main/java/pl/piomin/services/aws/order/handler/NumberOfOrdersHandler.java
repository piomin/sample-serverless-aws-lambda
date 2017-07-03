package pl.piomin.services.aws.order.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.piomin.services.aws.order.model.Account;
import pl.piomin.services.aws.order.model.Order;

public class NumberOfOrdersHandler implements RequestHandler<SNSEvent, Object> {

	private final static int ORDER_COUNT_THRESHOLD = 10;
	private final static int AMOUNT_BONUS_PERCENTAGE = 15;
	
	private DynamoDBMapper mapper;
	private ObjectMapper jsonMapper;
	
	public NumberOfOrdersHandler() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		mapper = new DynamoDBMapper(client);
		jsonMapper = new ObjectMapper();
	}
	
	@Override
	public Object handleRequest(SNSEvent event, Context context) {
		final LambdaLogger logger = context.getLogger();
		final List<SNSRecord> records = event.getRecords();
		
		for (SNSRecord record : records) {
			logger.log(String.format("SNSEvent: %s", record.getSNS().getMessageId()));
			try {
				final Order o = jsonMapper.readValue(record.getSNS().getMessage(), Order.class);
				Map<String, AttributeValue> m = new HashMap<>();
				m.put(":accountId", new AttributeValue().withS(o.getAccountId()));
				
				DynamoDBQueryExpression<Order> qe = new DynamoDBQueryExpression<Order>()
						.withKeyConditionExpression("accountId = :accountId")
						.withConsistentRead(false)
						.withExpressionAttributeValues(m);
				final int count = mapper.count(Order.class, qe);
				
				if (count > ORDER_COUNT_THRESHOLD) {
					logger.log(String.format("Order allowed: id=%s, amount=%d", o.getId(), o.getAmount()));
					Account a = mapper.load(Account.class, o.getId());
					a.setBalance(a.getBalance() + o.getAmount() * AMOUNT_BONUS_PERCENTAGE);
					mapper.save(a);
					logger.log(String.format("Account balande update: id=%s, amount=%d", a.getId(), a.getBalance()));
				}
			} catch (IOException e) {
				logger.log(e.getMessage());
			}
		}
		
		return "OK";
	}

}
