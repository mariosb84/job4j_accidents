package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "user_id")
    private int id;
    @NonNull
    @Column(name = "user_password")
    private String password;
    @NonNull
    @Column(name = "user_name")
    private String username;
    @ManyToOne
    @JoinColumn(name = "authority_id")
    @NonNull
    private Authority authority;
    @Column(name = "enabled")
    private boolean enabled;

}
