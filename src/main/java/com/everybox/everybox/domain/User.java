package com.everybox.everybox.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = true)
    private String password;

    private String nickname;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @Column(unique = true, nullable = true)
    private String universityEmail;

    @Builder.Default
    private Long experience = 0L;

    @Builder.Default
    private Long level = 1L;

    public void gainExperience(long amount) {
        this.experience += amount;

        while (this.experience >= level * 100) {
            this.experience %= 100;
            this.level++;
        }
    }
}
