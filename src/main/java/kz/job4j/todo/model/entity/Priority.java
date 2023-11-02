package kz.job4j.todo.model.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "priorities")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private int position;
}