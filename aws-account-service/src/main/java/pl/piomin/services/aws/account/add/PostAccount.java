package pl.piomin.services.aws.account.add;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import pl.piomin.services.aws.account.model.Account;

public class PostAccount implements RequestHandler<Account, Account> {

	@Override
	public Account handleRequest(Account a, Context ctx) {
		LambdaLogger logger = ctx.getLogger();
		logger.log("Account: " + a.getId());
		Account r = null;
		return r;
	}

}
