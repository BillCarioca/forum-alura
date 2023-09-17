package com.forumalura.domain.courses;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 170)
    private String name;
    @Column(nullable = false, length = 170)
    private String category;
    @Column(nullable = false)
    private boolean active = true;
}
