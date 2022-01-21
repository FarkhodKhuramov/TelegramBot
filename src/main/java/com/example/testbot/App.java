package com.example.testbot;

// import com.testvagrant.ekam.api.initializers.APIContextInitializer;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class App 
{
    public static void main(String[] args) {
        // APIContextInitializer.init();
        System.out.println("Hello world!");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot((TelegramLongPollingBot) new MyBot());
            
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
