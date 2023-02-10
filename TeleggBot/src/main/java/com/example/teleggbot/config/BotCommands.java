package com.example.teleggbot.config;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info"),
            new BotCommand("/post","post bot")
    );

    String HELP_TEXT = "Этот бот может попостить поостики. " +
            "Вот вам доступны следующие команды :\n\n" +
            "/start - это он стартует\n" +
            "/help - можешь глянуть помощь" +
            "/post -  а это он постит что то, пока рекламку на телег канал, подпишись чтоль)";


    String POST_TEXT =   "https://t.me/+gFCT63NlnfE5YWZi";
}
