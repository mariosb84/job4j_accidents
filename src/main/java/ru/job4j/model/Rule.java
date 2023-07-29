package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidentRules")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "accidentRule_id")
    private int id;
    @NonNull
    @Column(name = "accidentRule_name")
    private String name;
}
