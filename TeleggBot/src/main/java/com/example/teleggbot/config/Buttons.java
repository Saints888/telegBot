package com.example.teleggbot.config;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    private static final InlineKeyboardButton POST_BUTTON = new InlineKeyboardButton("Post");

    public static InlineKeyboardMarkup inlineKeyboardMarkup() {
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");
        POST_BUTTON.setCallbackData("/post");

        List<InlineKeyboardButton> rowInLine = List.of(START_BUTTON, HELP_BUTTON, POST_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInLine);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }
}
