package pl.piomin.services.aws.customer.contract;

import java.util.List;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

import pl.piomin.services.aws.customer.model.Account;

public interface AccountService {

	@LambdaFunction(functionName = "GetAccountsByCustomerIdFunction")
	List<Account> getAccountsByCustomerId(Account account);
}
