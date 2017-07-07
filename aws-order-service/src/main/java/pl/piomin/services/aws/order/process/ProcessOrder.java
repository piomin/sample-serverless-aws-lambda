package pl.piomin.services.aws.order.process;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.piomin.services.aws.order.message.OrderMessage;
import pl.piomin.services.aws.order.model.Order;

public class ProcessOrder implements RequestHandler<DynamodbEvent, String> {

	private AmazonSNSClient client;
	private ObjectMapper jsonMapper;
	
	public ProcessOrder() {
		client = new AmazonSNSClient();
		jsonMapper = new ObjectMapper();
	}
	
	public String handleRequest(DynamodbEvent event, Context ctx) {
		LambdaLogger logger = ctx.getLogger();	
		final List<DynamodbStreamRecord> records = event.getRecords();
		
		for (DynamodbStreamRecord record : records) {
			try {
				logger.log(String.format("DynamoEvent: %s, %s", record.getEventName(), record.getDynamodb().getNewImage().values().toString()));
				Map<String, AttributeValue> m = record.getDynamodb().getNewImage();
				Order order = new Order(m.get("id").getS(), m.get("accountId").getS(), Integer.parseInt(m.get("amount").getN()));
				String msg = jsonMapper.writeValueAsString(order);
				logger.log(String.format("SNS message: %s", msg));
				PublishRequest req = new PublishRequest("arn:aws:sns:us-east-1:658226682183:order", jsonMapper.writeValueAsString(new OrderMessage(msg)), "Order");
				req.setMessageStructure("json");
				PublishResult res = client.publish(req);
				logger.log(String.format("SNS message sent: %s", res.getMessageId()));
			} catch (JsonProcessingException e) {
				logger.log(e.getMessage());
			}
		}
		
		return "OK";
	}
}
