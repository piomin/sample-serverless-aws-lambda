package pl.piomin.services.aws.account.find;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.account.model.Account;

public class GetAccountsByCustomerId implements RequestHandler<Account, List<Account>> {

	private DynamoDBMapper mapper;
	
	public GetAccountsByCustomerId() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(Regions.US_EAST_1));
		mapper = new DynamoDBMapper(client);
	}
	
	@Override
	public List<Account> handleRequest(Account account, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		logger.log("Account: " + account.getId());
		
		Map<String, AttributeValue> m = new HashMap<>();
		m.put(":customerId", new AttributeValue().withS(String.valueOf(account.getCustomerId())));
		
		DynamoDBQueryExpression<Account> qe = new DynamoDBQueryExpression<Account>()
				.withIndexName("Customer-Index")
				.withKeyConditionExpression("customerId = :customerId")
				.withExpressionAttributeValues(m);

		return mapper.query(Account.class, qe);
	}

}
