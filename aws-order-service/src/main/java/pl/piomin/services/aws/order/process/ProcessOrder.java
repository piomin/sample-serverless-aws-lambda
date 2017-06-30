package pl.piomin.services.aws.order.process;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

public class ProcessOrder implements RequestHandler<DynamodbEvent, String> {

	public String handleRequest(DynamodbEvent ddbEvent, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		
		AmazonKinesisClient client = new AmazonKinesisClient();
		
		PutRecordsRequest req  = new PutRecordsRequest();
		
		
		ddbEvent.getRecords().forEach(it -> logger.log(it.getEventID()));
		return "OK";
	}
}
