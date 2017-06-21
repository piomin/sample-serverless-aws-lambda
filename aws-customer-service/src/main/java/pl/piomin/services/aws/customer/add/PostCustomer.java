package pl.piomin.services.aws.customer.add;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.customer.model.Customer;

public class PostCustomer implements RequestHandler<Customer, Customer> {

	private DynamoDBMapper mapper;
	
	public PostCustomer() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(Regions.US_EAST_1));
		mapper = new DynamoDBMapper(client);
	}
	
	@Override
	public Customer handleRequest(Customer c, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		mapper.save(c);		
		Customer r = c;
		logger.log("Account: " + r.getId());
		return r;
	}

}
