package com.example.teleggbot.config;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Iterator;

import static com.example.teleggbot.config.BotCommands.HELP_TEXT;
import static com.example.teleggbot.config.BotCommands.LIST_OF_COMMANDS;
import static com.example.teleggbot.config.BotCommands.*;

@Slf4j
@Component
public class CounterTelegramBot extends TelegramLongPollingBot {





    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private UserRepository userRepository;

    final BotConfig config;

    public CounterTelegramBot(BotConfig config) {
        this.config = config;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));

        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        long chatId = 0;
       long userId = 0;
        String userName = null;
        String receivedMessage = null;

        //если получено сообщение текстом

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getData();


            botAnswerUtils(receivedMessage, chatId, userName);
        }


    }


    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage) {

            case "/start":
                startBot(chatId, userName);
                break;
            case "/help":
                sendHelpText(chatId, HELP_TEXT);
                break;
            case "/post":
                sendPost(chatId, POST_TEXT);

            default:


                break;
        }


    }


    private void sendPost(long chatId, String autoPost) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(autoPost);


        try {
            execute(message);
            log.info("Reply sent");

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendHelpText(long chatId, String helpText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(helpText);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    @Scheduled(cron = "0 * * * * *")
    private void sendAds() {

        var ads = adsRepository.findAll();
        var users = userRepository.findAll();


        for (Ads ad: ads) {
            for (User user: users) {
                sendPost(user.getChatId(), ad.getAdd());

            }

    }

}




    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Приветули, " + userName + "   тестовый бот воззможно буду постить новости");

        try {
            execute(message);
            log.info("Reply sent");

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


}




