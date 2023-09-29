package it.marczuk.aiassistantdctemplate.bot.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import it.marczuk.aiassistantdctemplate.web.model.AssistantMessage;
import it.marczuk.aiassistantdctemplate.web.model.AssistantResponse;
import it.marczuk.aiassistantdctemplate.web.model.MessageGroup;
import it.marczuk.aiassistantdctemplate.web.model.MessageType;
import it.marczuk.aiassistantdctemplate.web.service.AssistantService;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.Command.Choice;

@Component
public class AskAssistant extends SlashCommand {

    private final AssistantService assistantService;

    public AskAssistant(AssistantService assistantService) {
        this.name = "ask-assistant";
        this.help = "Command to ask your AI assistant";
        this.ownerCommand = true;
        this.options = List.of(
                new OptionData(
                        OptionType.STRING,
                        "type",
                        "Select type",
                        true
                ).addChoices(
                        new Choice("query", "QUERY"),
                        new Choice("save", "SAVE"),
                        new Choice("forget", "FORGET")
                ),
                new OptionData(
                        OptionType.STRING,
                        "group",
                        "Select group",
                        true
                ).addChoices(
                        new Choice("memories", "MEMORIES"),
                        new Choice("notes", "NOTES"),
                        new Choice("links", "LINKS"),
                        new Choice("actions", "ACTIONS")
                ),
                new OptionData(
                        OptionType.STRING,
                        "query",
                        "Type your content...",
                        true
                ).setMaxLength(2500)
        );

        this.assistantService = assistantService;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        OptionMapping type = event.getOption("type");
        OptionMapping group = event.getOption("group");
        OptionMapping query = event.getOption("query");

        if (type == null || group == null || query == null) {
            event.reply(":warning: You need to type the option")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        AssistantResponse response = assistantService.interactWithAiAssistant(
                new AssistantMessage(
                        query.getAsString(),
                        MessageType.valueOf(type.getAsString()),
                        MessageGroup.valueOf(group.getAsString())
                )
        );
        event.reply(response.getAnswer()).queue();
    }
}
