package org.mytelegrambot.core.assemble;

import org.mytelegrambot.computer.Computer;
import org.mytelegrambot.core.datacontrol.ProcessedData;
import org.mytelegrambot.core.utils.KeyboardGenerator;
import org.mytelegrambot.enums.DataPrefixEnum;
import org.mytelegrambot.enums.OptionsToSendEnum;
import org.mytelegrambot.exceptions.ComponentNotFoundException;
import org.mytelegrambot.exceptions.ComponentStorageException;
import org.mytelegrambot.text.TextContainer;
import org.mytelegrambot.users.User;
import org.mytelegrambot.users.UsersService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class BuildProcessService {
    private final BuildProcess buildProcess;

    public BuildProcessService() {
        buildProcess = new BuildProcess();
    }

    public void build(Message message, ProcessedData processedData) {
        User user = UsersService.getUser(message);
        Computer computer;

        try {
            computer = buildProcess.build(user.getMoney(), user.getBrandCPU(), user.getBrandGPU());
        } catch (ComponentStorageException e) {
            e.printStackTrace();
            processedData.setMessageText("Ошибка, попробуйте позже");
            processedData.setOptionToSend(OptionsToSendEnum.SEND);
            return;
        } catch (ComponentNotFoundException e) {
            processedData.setMessageText(TextContainer.pcBuildFailed);
            processedData.setOptionToSend(OptionsToSendEnum.SEND);
            return;
        }

        user.setLastComputer(computer);

        processedData.setMessageText(computer.getInfo(false, ""));
        processedData.setOptionToSend(OptionsToSendEnum.SENDWITHKEYBOARD);
        processedData.setKeyboardMarkup(
                KeyboardGenerator.generateInlineKeyboard(List.of("Сохранить"), DataPrefixEnum.PRIVATEPC.toString()));
    }
}
