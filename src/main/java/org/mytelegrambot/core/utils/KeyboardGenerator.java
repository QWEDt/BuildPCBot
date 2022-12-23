package org.mytelegrambot.core.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KeyboardGenerator {
    public static InlineKeyboardMarkup generateInlineKeyboard(Collection<String> words, String callBackDataPrefix) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline;
        InlineKeyboardButton button;

        for (String word : words) {
            rowInline = new ArrayList<>();
            button = new InlineKeyboardButton();
            button.setText(word);
            button.setCallbackData(callBackDataPrefix + word);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        keyboardMarkup.setKeyboard(rowsInline);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup generateKeyboard(List<String> words) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        for (String word : words) {
            row.add(word);
        }

        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
