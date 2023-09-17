package com.forumalura.domain.users;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 170)
    private String name;
    @Column(nullable = false, length = 70,unique = true)
    private String email;
    @Column(nullable = false, length = 70)
    private String password;
    @Column(nullable = false, length = 10)
    private String role;
    @Column(nullable = false)
    private boolean active = true;
}
