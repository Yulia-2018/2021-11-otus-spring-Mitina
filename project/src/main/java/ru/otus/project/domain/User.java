package ru.otus.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must have size between 2 and 50")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 150, message = "Password must have size between 5 and 150")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telegram_username")
    private String telegramUsername;

    @Column(name = "telegram_chat_id")
    private Long telegramChatId;

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;

    public void addRole(Role role) {
        if (role != null) {
            if (roles == null) {
                roles = new HashSet<>();
            }
            roles.add(role);
            role.setUser(this);
        }
    }

    public int compareById(User u2) {
        return id.compareTo(u2.id);
    }

    public User(String name, String password, String telegramUsername) {
        this.name = name;
        this.password = password;
        this.telegramUsername = telegramUsername;
    }

    public User(String name, String password, String telegramUsername, Set<Role> roles) {
        this(name, password, telegramUsername);
        this.roles = roles;
    }

    public User(Long id, String name, String password, String telegramUsername, Set<Role> roles) {
        this(name, password, telegramUsername, roles);
        this.id = id;
    }
}
