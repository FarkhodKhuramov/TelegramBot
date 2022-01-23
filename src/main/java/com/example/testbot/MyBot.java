package com.example.testbot;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    private Set<String> owners;
    // КОМАНДЫ
    private final String COMMAND_SEPARATOR = "=";
    private final String COMMAND_RULES = "/rules";
    private final String COMMAND_TARIFS = "/tarifs";
    private final String COMMAND_NEW_RULES = "/setNewRules" + COMMAND_SEPARATOR;
    private final String COMMAND_NEW_TARIFS = "/setNewTarifs" + COMMAND_SEPARATOR;
    private final String COMMAND_ADD_OWNER = "/addOwner" + COMMAND_SEPARATOR;
    private final String COMMAND_REMOVE_OWNER = "/removeOwner" + COMMAND_SEPARATOR;
    private final String COMMAND_SHOW_OWNERS = "/showOwners";
    

    public MyBot(){
        String owner1 = "Farhod_10";
        String owner2 = "azizimnzrv";
        owners = new HashSet<>(Arrays.asList(owner1, owner2));
    }

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
        
        // ДОБАВЛЕН НОВЫЙ УЧАСТНИК, ПОКАЗЫВАЕМ ПРАВИЛА
        if(arg0.getMessage().getNewChatMembers().size()>0){
            msg.setText(rules);
            msg.setReplyToMessageId(null);
            sendMsg(msg);
            return;
        }
        Message message;
        // ЧИТАЕМ СООБЩЕНИЕ
        if(arg0.hasMessage() && arg0.getMessage().hasText()){
            System.out.println(arg0.getMessage().getText());
            message = arg0.getMessage();
        }else{
            System.out.println("No text in the message or no message");
            return;
        }    
        
        // ПРАВИЛА
        if(message.getText().startsWith(COMMAND_RULES)){
            System.out.println(COMMAND_RULES);
            msg.setText(rules);
        } 
        
        // ТАРИФЫ        
        if(message.getText().startsWith(COMMAND_TARIFS)){
            System.out.println(COMMAND_TARIFS);
            msg.setText(tarifs);
        } 
    
        // ПРОВЕРКА НА ВЛАДЕЛЬЦА
        if(!isOwner(message.getFrom().getUserName())){
            System.out.println("user is not owner: " + message.getFrom().getUserName());
            return;
        }

        // ЗАДАТЬ НОВЫЕ ПРАВИЛА        
        if(message.getText().startsWith(COMMAND_NEW_RULES)){
            System.out.println();
            rules = message.getText().substring(message.getText().indexOf(COMMAND_SEPARATOR)+1).trim();
            msg.setText("The rules have just been changed");
        }
        
        // ЗАДАТЬ НОВЫЕ ТАРИФЫ        
        if(message.getText().startsWith(COMMAND_NEW_TARIFS)){
            System.out.println(COMMAND_NEW_TARIFS);
            tarifs = message.getText().substring(message.getText().indexOf(COMMAND_SEPARATOR)+1).trim();
            msg.setText("The Tarifs have just been changed");
        }

        // ДОБАВИТЬ ВЛАДЕЛЬЦА
        if(message.getText().startsWith(COMMAND_ADD_OWNER)){
            System.out.println(COMMAND_ADD_OWNER);
            addOwner(message.getText().substring(message.getText().indexOf(COMMAND_SEPARATOR) + 1).trim());
        }

        // УДАЛИТЬ ВЛАДЕЛЬЦА
        if(message.getText().startsWith(COMMAND_REMOVE_OWNER)){
            System.out.println(COMMAND_REMOVE_OWNER);
            removeOwner(message.getText().substring(message.getText().indexOf(COMMAND_SEPARATOR) + 1).trim());
        }

        // ПОКАЗАТЬ ВСЕХ ВЛАДЕЛЬЦЕВ    
        if(message.getText().startsWith(COMMAND_SHOW_OWNERS)){
            System.out.println(COMMAND_SHOW_OWNERS);
            showOwners(msg);
        }

    }

    private void showOwners(SendMessage msg) {
        StringBuilder msgText = new StringBuilder();
        for (String string : owners) {
            msgText.append(string + "\n");
        }
        msg.setText(msgText.toString());
        sendMsg(msg);
    }

    private void sendMsg(SendMessage message){
        try{
            System.out.println("response: " + message.getText());
            execute(message);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }

    private boolean isOwner(String username){
        for (String string : owners) {
            if(username.equals(string))
                return true;
        }
        return false;
    }

    private void addOwner (String username){
        owners.add(username);
        
    }

    private void removeOwner (String username){
        owners.removeIf(s -> s.equals(username));
    }

    
}