package pl.piomin.services.aws.account;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.lambda.runtime.tests.EventLoader;
import org.junit.jupiter.api.Test;

import java.beans.EventHandler;

public class AccountServiceTests {

    @Test
    void postAccount() {
        ScheduledEvent evn = EventLoader.loadScheduledEvent("account.json");
    }
}
