package com.example.testbot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

 public class MyBot extends TelegramLongPollingBot{ 

    private final static String TOKEN = "5166396671:AAGflOsFm5anj120y15srGcvv0p543BTlFQ";
    private final static String BOT_NAME = "FarkhodKh_bot";
    private String rules = "Правила не установлены";
    private String tarifs = "Тарифы не установлены";
    private final String owner = "farhod_10";

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update arg0) {
        
        System.out.println(String.format("%s %s: %s", arg0.getMessage().getFrom().getFirstName(), arg0.getMessage().getFrom().getLastName(), arg0.getMessage().getText())); 
        System.out.println("Update: " + arg0);
        
        SendMessage msg = new SendMessage();
        msg.setChatId(arg0.getMessage().getChatId().toString());
        msg.setReplyToMessageId(arg0.getMessage().getMessageId());
        
        if(arg0.getMessage().getNewChatMembers().size()>0){
            msg.setText(rules);
            msg.setReplyToMessageId(null);
        }else if(arg0.hasMessage() && arg0.getMessage().hasText()){
            Message message = arg0.getMessage();
        // ПРАВИЛА
            if(message.getText().startsWith("/rules")){
                msg.setText(rules);
        // ТАРИФЫ        
            }else if(message.getText().startsWith("/tarifs")){
                msg.setText(tarifs);
        // ЗАДАТЬ НОВЫЕ ПРАВИЛА        
            }else if(message.getText().startsWith("/setNewRules:") && message.getFrom().getUserName().equalsIgnoreCase(owner)){
                rules = message.getText().substring(message.getText().indexOf(":")+1).trim();
                msg.setText("The rules have just been changed");
        // ЗАДАТЬ НОВЫЕ ТАРИФЫ        
            }else if(message.getText().startsWith("/setNewTarifs:") && message.getFrom().getUserName().equalsIgnoreCase(owner)){
                tarifs = message.getText().substring(message.getText().indexOf(":")+1).trim();
                msg.setText("The Tarifs have just been changed");
            }
        }
                
        try {
			execute(msg);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
    }

    
}