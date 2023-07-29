package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "accident_id")
    private int id;
    @NonNull
    @Column(name = "accident_name")
    private String name;
    @NonNull
    @Column(name = "accident_text")
    private String text;
    @NonNull
    @Column(name = "accident_address")
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accidentTypes_id")
    @NonNull
    private AccidentType type;
    @ManyToMany
    @JoinTable(
            name = "link",
            joinColumns = { @JoinColumn(name = "accidentLink_id") },
            inverseJoinColumns = { @JoinColumn(name = "rules_id") }
    )
    private Set<Rule> rules = new HashSet<>();
}