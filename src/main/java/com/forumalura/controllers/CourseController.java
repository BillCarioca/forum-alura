package com.forumalura.controllers;

import com.forumalura.domain.courses.Course;
import com.forumalura.domain.courses.CourseCreateDTO;
import com.forumalura.domain.courses.CourseUpdateDTO;
import com.forumalura.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@SecurityRequirement(name = "Forum")
@Tag(name = "Courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Operation(summary = "Create a course in Database")
    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CourseCreateDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(new Course(requestDTO)));
    }

    @Operation(summary = "Find all courses in Database")
    @GetMapping("/all")
    public ResponseEntity<Page<Course>> getAllCourse(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(courseService.findAll(pageable));
    }

    @Operation(summary = "Find all courses actives in Database")
    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourseActive(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(courseService.findAllActive(pageable));
    }

    @Operation(summary = "Find a course in Database by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getByIdCourse(@Parameter(description = "Id of Course to be Searched")
                                              @PathVariable(value = "id") Long id){
        Optional<Course> optional = courseService.findById(id);
        return optional.isPresent()
                ? ResponseEntity.ok(optional.get())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }

    @Operation(summary = "Update a course in Database")
    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody @Valid CourseUpdateDTO requestDTO){
        if (courseService.existById(requestDTO.id()))
            return ResponseEntity.ok(courseService.save(new Course(requestDTO)));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }

    @Operation(summary = "Disable a course in database by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableCourse (@Parameter(description = "Id of Course to be Disabled")
                                               @PathVariable(value = "id") Long id){
        if (courseService.existById(id)) {
            courseService.disable(id);
            return ResponseEntity.noContent().eTag("Course disabled successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }

    @Operation(summary = "Delete a course in database by its id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCourse(@Parameter(description = "Id of Course to be Deleted")
                                              @PathVariable(value = "id") Long id){
        if (courseService.existById(id)){
            courseService.delete(id);
            return  ResponseEntity.noContent().eTag("Course deleted successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }
}
