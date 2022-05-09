package ru.otus.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 120)
    @Column(name = "description", nullable = false)
    private String description;

    @FutureOrPresent(message = "Deadline cannot be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @NotNull
    @Column(name = "done", nullable = false)
    private Boolean done;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public int compareByDoneAndDeadline(Task t2) {
        if (done.compareTo(t2.done) > 0) {
            return 1;
        } else if (done.compareTo(t2.done) < 0) {
            return -1;
        } else {
            if (done) {
                return t2.deadline.compareTo(deadline);
            } else {
                return deadline.compareTo(t2.deadline);
            }
        }
    }

    public Task(Long id, String description, LocalDate deadline, Boolean done) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                '}';
    }
}
