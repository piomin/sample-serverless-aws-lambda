package pl.piomin.services.aws.order.process;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class ProcessOrder implements RequestHandler<DynamodbEvent, String> {

	public String handleRequest(DynamodbEvent ddbEvent, Context ctx) {
		LambdaLogger logger = ctx.getLogger();	
		logger.log("DB event");
		ddbEvent.getRecords().forEach(it -> logger.log("Values: " + it.getEventName() + "   " + it.getDynamodb().getNewImage().values().toString()));
		
		AmazonSNSClient client = new AmazonSNSClient();
		PublishRequest req = new PublishRequest("arn:aws:sns:us-east-1:658226682183:order", "{\"default\":\"Hi\", \"name\":\"1\"}", "Order");
		req.setMessageStructure("json");
		PublishResult res = client.publish(req);
		logger.log("RES: " + res.getMessageId());
		return "OK";
	}
}
