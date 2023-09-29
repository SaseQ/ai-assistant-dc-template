package it.marczuk.aiassistantdctemplate.bot.configuration;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import it.marczuk.aiassistantdctemplate.bot.Bot;
import it.marczuk.aiassistantdctemplate.bot.command.AskAssistant;
import it.marczuk.aiassistantdctemplate.bot.command.Ping;
import it.marczuk.aiassistantdctemplate.bot.exception.JdaException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Configuration
@Slf4j
public class CommandConfig {

    private JDA jda;
    private final Dotenv env;

    private final Ping ping;
    private final AskAssistant askAssistant;

    @Autowired
    public CommandConfig(Ping ping, AskAssistant askAssistant) throws LoginException {
        env = Bot.getInstance().getEnv();

        this.ping = ping;
        this.askAssistant = askAssistant;
    }

    @PostConstruct
    private void init() {
        try {
            jda = Bot.getInstance().getJda();
            jda.addEventListener(getBotConfig());
        } catch (LoginException e) {
            throw new JdaException("Jda not exists");
        }
    }

    private CommandClient getBotConfig() {
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId(env.get("OWNER_ID"));
        builder.forceGuildOnly(env.get("FORCE_GUILD"));
        builder.setActivity(Activity.watching(env.get("ACTIVITY")));

        // Registration SlashCommands
        builder.addSlashCommand(ping);
        builder.addSlashCommand(askAssistant);

        // Registration ListenerAdapters
        jda.addEventListener();

        log.info("Jda configuration loaded!");
        return builder.build();
    }
}
