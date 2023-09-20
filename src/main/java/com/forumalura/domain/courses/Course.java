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
    private boolean activeDate = true;
    public Course (CourseCreateDTO dto){
        name= dto.name();
        category= dto.category();
    }
    public Course (CourseUpdateDTO dto){
        id = dto.id();
        name= dto.name();
        category= dto.category();
    }
}
