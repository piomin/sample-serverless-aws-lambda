package pl.piomin.services.aws.account.find;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.account.model.Account;

public class GetAccount implements RequestHandler<Account, Account> {

	private DynamoDBMapper mapper;
	
	public GetAccount() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(Regions.US_EAST_1));
		mapper = new DynamoDBMapper(client);
	}
	
	@Override
	public Account handleRequest(Account account, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		logger.log("Account: " + account.getId());
		account = mapper.load(Account.class, account.getId());
		return account;
	}

}
