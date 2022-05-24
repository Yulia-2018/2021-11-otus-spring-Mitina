package ru.otus.project.telegram;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.otus.project.domain.Task;
import ru.otus.project.domain.User;
import ru.otus.project.repository.TaskRepository;
import ru.otus.project.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class TaskBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

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
                                .text(userName + " welcome to 'Personal task management system'.\n" +
                                        "Now you every day will receive a list of your tasks for today in telegram.")
                                .build());
                    } else {
                        execute(SendMessage.builder()
                                .chatId(chatId.toString())
                                .text("Hello " + userName + ".\n" +
                                        "We didn't find you in 'Personal task management system'.\n" +
                                        "Either you haven't registered yet, or you at registration filled in field 'Telegram username' incorrectly. Please check it out.")
                                .build());
                    }
                } else {
                    execute(SendMessage.builder()
                            .chatId(chatId.toString())
                            .text("Hello. Your username in telegram is empty. Please fill it out.")
                            .build());

                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void send() {
        List<User> users = userRepository.getByTelegramChatIdIsNotNull();
        users.forEach(user -> {
            List<Task> tasksForToday = taskRepository.getByUserIdAndDeadlineAndDoneIsFalse(user.getId(), LocalDate.now());
            if (!tasksForToday.isEmpty()) {
                StringBuilder messageText = new StringBuilder();
                tasksForToday.forEach(task -> messageText.append(task.getDescription()).append("\n"));
                try {
                    execute(SendMessage.builder()
                            .chatId(user.getTelegramChatId().toString())
                            .text("Your tasks for today:\n" +
                                    messageText.toString())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}