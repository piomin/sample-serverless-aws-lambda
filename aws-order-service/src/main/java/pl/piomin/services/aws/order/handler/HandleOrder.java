package pl.piomin.services.aws.order.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class HandleOrder implements RequestHandler<SNSEvent, Object> {

	public Object handleRequest(SNSEvent request, Context ctx) {
		LambdaLogger logger = ctx.getLogger();	
		logger.log("SNS event");
		
		logger.log("MSG: " + request.getRecords().get(0).getSNS().getMessage());
		
		return null;
	}
}
