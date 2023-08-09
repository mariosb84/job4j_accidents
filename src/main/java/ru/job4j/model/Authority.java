package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "authorities")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "authorities_id")
    private int id;
    @NonNull
    @Column(name = "authority")
    private String authority;

}
