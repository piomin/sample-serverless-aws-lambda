package pl.piomin.services.aws.customer.find;

import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.customer.contract.AccountService;
import pl.piomin.services.aws.customer.model.Account;
import pl.piomin.services.aws.customer.model.Customer;

public class GetCustomer implements RequestHandler<Customer, Customer> {

	private DynamoDBMapper mapper;
	private AccountService accountService;
	
	public GetCustomer() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(Regions.US_EAST_1));
		mapper = new DynamoDBMapper(client);
		
		accountService = LambdaInvokerFactory.builder() .lambdaClient(AWSLambdaClientBuilder.defaultClient())
				 .build(AccountService.class);
	}
	
	@Override
	public Customer handleRequest(Customer customer, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		logger.log("Account: " + customer.getId());
		customer = mapper.load(Customer.class, customer.getId());
		List<Account> aa = accountService.getAccountsByCustomerId(new Account(customer.getId()));
		customer.setAccounts(aa);
		return customer;
	}

}
