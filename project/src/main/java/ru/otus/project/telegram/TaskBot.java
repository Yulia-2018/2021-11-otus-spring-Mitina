package ru.otus.project.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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
            if (!userName.isEmpty()) {
                Optional<User> optionalUser = userRepository.getByTelegramUsername(userName);
                if (optionalUser.isPresent()) {
                    Long chatId = message.getChatId();
                    User user = optionalUser.get();
                    user.setTelegramChatId(chatId);
                    userRepository.save(user);
                }
            }
        }
    }
}