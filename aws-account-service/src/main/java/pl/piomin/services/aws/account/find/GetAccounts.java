package pl.piomin.services.aws.account.find;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.account.model.Account;

public class GetAccounts implements RequestHandler<Account, String> {

	@Override
	public String handleRequest(Account account, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		logger.log("Account: " + account.getId());
		return "Hello";
	}

}
