package kz.job4j.todo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime created;
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(String description, Boolean done) {
        this.description = description;
        this.done = done;
    }
}
