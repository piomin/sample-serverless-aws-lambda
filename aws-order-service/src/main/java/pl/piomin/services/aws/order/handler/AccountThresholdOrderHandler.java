package pl.piomin.services.aws.order.handler;

import java.io.IOException;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.piomin.services.aws.order.model.Account;
import pl.piomin.services.aws.order.model.Order;

public class AccountThresholdOrderHandler implements RequestHandler<SNSEvent, Object> {

	private final static int ACCOUNT_BALANCE_THRESHOLD = 5000;
	private final static int AMOUNT_BONUS_PERCENTAGE = 15;
	
	private DynamoDBMapper mapper;
	private ObjectMapper jsonMapper;
	
	public AccountThresholdOrderHandler() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		mapper = new DynamoDBMapper(client);
		jsonMapper = new ObjectMapper();
	}
	
	@Override
	public Object handleRequest(SNSEvent event, Context context) {
		final LambdaLogger logger = context.getLogger();
		final List<SNSRecord> records = event.getRecords();
		
		for (SNSRecord record : records) {
			logger.log(String.format("SNSEvent: %s, %s", record.getSNS().getMessageId(), record.getSNS().getMessage()));
			try {
				final Order o = jsonMapper.readValue(record.getSNS().getMessage(), Order.class);
				final Account a = mapper.load(Account.class, o.getAccountId());
				
				if (a.getBalance() > ACCOUNT_BALANCE_THRESHOLD) {
					logger.log(String.format("Order allowed: id=%s, amount=%d", o.getId(), o.getAmount()));
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
