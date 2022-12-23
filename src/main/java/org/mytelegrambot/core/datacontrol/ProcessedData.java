package org.mytelegrambot.core.datacontrol;

import org.mytelegrambot.enums.OptionsToSendEnum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class ProcessedData implements ReturnData {
    private String messageText;
    private OptionsToSendEnum optionToSend;
    private ReplyKeyboard keyboardMarkup;
    private final boolean isBuildingPC;

    public ProcessedData(boolean isBuildingPC) {
        this.isBuildingPC = isBuildingPC;
        this.messageText = null;
        this.optionToSend = null;
        this.keyboardMarkup = null;
    }

    public ProcessedData(String messageText, OptionsToSendEnum optionToSend, ReplyKeyboard keyboardMarkup) {
        this.messageText = messageText;
        this.optionToSend = optionToSend;
        this.keyboardMarkup = keyboardMarkup;
        this.isBuildingPC = false;
    }

    public boolean isBuildingPC() {
        return isBuildingPC;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public OptionsToSendEnum getOptionToSend() {
        return optionToSend;
    }

    public void setOptionToSend(OptionsToSendEnum optionToSend) {
        this.optionToSend = optionToSend;
    }

    public ReplyKeyboard getKeyboardMarkup() {
        return keyboardMarkup;
    }

    public void setKeyboardMarkup(ReplyKeyboard keyboardMarkup) {
        this.keyboardMarkup = keyboardMarkup;
    }
}
