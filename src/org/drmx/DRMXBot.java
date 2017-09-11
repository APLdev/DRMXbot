package org.drmx;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class DRMXBot extends TelegramLongPollingBot {

    public static final String BOT_USERNAME = "drmxbot";
    public static final String BOT_TOKEN = "317500028:AAFOfw1c-GXGSAttMzRIBk6E_VJV8gjfkK0";


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new DRMXBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals(Commands.HELP.getId())) {
                sendMsg(message, "Привет, я робот, меня зовут " + BOT_USERNAME + "\nВот список моих команд:\n  " + getCommands());
            } else if(message.getText().equals(Commands.CINEMA_TODAY.getId())) {
                sendMsg(message, "Ща посмотрим...");
                sendMsg(message, CinemaViewer.getSeances());
            }  else if(message.getText().equals(Commands.OWNER.getId())) {
                    sendMsg(message, "Мой хозяин Максим");
            } else {
                sendMsg(message, "Я не знаю что ответить на это");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Commands commands : Commands.values()) {
            stringBuilder.append("<b>" + commands.getId() + "</b> : " + commands.getDescription() + "\n");
        }
        return stringBuilder.toString();
    }
}
