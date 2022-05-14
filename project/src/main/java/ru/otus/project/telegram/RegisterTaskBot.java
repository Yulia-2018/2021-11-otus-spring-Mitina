package ru.otus.project.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.otus.project.repository.TaskRepository;
import ru.otus.project.repository.UserRepository;

@Component
public class RegisterTaskBot {

    public RegisterTaskBot(UserRepository userRepository, TaskRepository taskRepository) throws TelegramApiException {
        TaskBot taskBot = new TaskBot(userRepository, taskRepository);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(taskBot);
    }
}
