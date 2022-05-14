package ru.otus.project.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.project.domain.User;
import ru.otus.project.repository.UserRepository;

import java.util.Optional;

public class TaskBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    public TaskBot(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return "@Task2022Bot";
    }

    @Override
    public String getBotToken() {
        return "5397746195:AAHrCPSYqQ7moGK_W0s63_tobxO526qEe5M";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String userName = message.getFrom().getUserName();
            Long chatId = message.getChatId();
            try {
                if (userName != null) {
                    Optional<User> optionalUser = userRepository.getByTelegramUsername(userName);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        user.setTelegramChatId(chatId);
                        userRepository.save(user);
                        execute(SendMessage.builder()
                                .chatId(chatId.toString())
                                .text(userName + " welcome to 'Personal task management system'. Now you every day will receive a list of your tasks for today in telegram.")
                                .build());
                    } else {
                        execute(SendMessage.builder()
                                .chatId(chatId.toString())
                                .text("Hello " + userName + "." +
                                        " We didn't find you in 'Personal task management system'." +
                                        " Either you haven't registered yet, or you at registration filled in field 'Telegram username' incorrectly. Please check it out.")
                                .build());
                    }
                } else {
                    execute(SendMessage.builder()
                            .chatId(chatId.toString())
                            .text("Hello. Your userName in telegram is empty. Please fill it out.")
                            .build());

                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}