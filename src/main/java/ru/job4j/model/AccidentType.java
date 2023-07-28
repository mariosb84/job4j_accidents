package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidentTypes")
@AllArgsConstructor
@NoArgsConstructor
public class AccidentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NonNull
    @Setter
    @Getter
    @Column(name = "accidentType_id")
    private int id;
    @NonNull
    @Setter
    @Getter
    @Column(name = "accidentType_name")
    private String name;
}
