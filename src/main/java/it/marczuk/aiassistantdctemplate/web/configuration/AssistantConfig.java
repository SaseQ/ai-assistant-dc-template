package it.marczuk.aiassistantdctemplate.web.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import it.marczuk.aiassistantdctemplate.bot.Bot;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class AssistantConfig {

    private final Dotenv env;

    public AssistantConfig() throws LoginException {
        env = Bot.getInstance().getEnv();
    }

    public String getURL() {
        return env.get("WEBHOOK_URL");
    }
}
