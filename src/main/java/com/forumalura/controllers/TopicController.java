package com.forumalura.controllers;

import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.topics.TopicCreateDTO;
import com.forumalura.domain.topics.TopicUpdateDTO;
import com.forumalura.services.AuthenticationFacade;
import com.forumalura.services.CourseService;
import com.forumalura.services.TopicService;
import com.forumalura.services.UserService;
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
@RequestMapping("/api/topics")
@SecurityRequirement(name = "Forum")
@Tag(name = "Topics")
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @Operation(summary = "Create a topics in Database")
    @PostMapping
    public ResponseEntity<Object> createTopic(@RequestBody @Valid TopicCreateDTO requestDTO){
        var course = courseService.findById(requestDTO.courseId()).get();
        var author = userService.findByEmail(authenticationFacade.getEmail()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.save(new Topic(requestDTO,course,author)));
    }

    @Operation(summary = "Find all topics in Database")
    @GetMapping("/all")
    public ResponseEntity<Page<Topic>> getAllTopic(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(topicService.findAll(pageable));
    }

    @Operation(summary = "Find all topics in Database where the user is the author")
    @GetMapping("/author")
    public ResponseEntity<Page<Topic>> getAllTopicByAuthor(@ParameterObject Pageable pageable){
        var author = userService.findByEmail(authenticationFacade.getEmail()).get();
        return ResponseEntity.ok(topicService.findAllByAuthor(author,pageable));
    }

    @Operation(summary = "Find all topics actives in Database")
    @GetMapping
    public ResponseEntity<Page<Topic>> getAllTopicActive(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(topicService.findAllActive(pageable));
    }

    @Operation(summary = "Find a topics in Database by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getByIdTopic(@Parameter(description = "Id of Topic to be Searched")
                                                @PathVariable(value = "id") Long id){
        Optional<Topic> optional = topicService.findById(id);
        return optional.isPresent()
                ? ResponseEntity.ok(optional.get())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
    }

    @Operation(summary = "Update a topics in Database")
    @PutMapping
    public ResponseEntity<Object> updateTopic(@RequestBody @Valid TopicUpdateDTO requestDTO){
        if (topicService.existById(requestDTO.id())){
            var topic = topicService.findById(requestDTO.id()).get();
            var course = courseService.findById(requestDTO.courseId()).get();
            topic.updateTopic(requestDTO,course);
            return ResponseEntity.ok(topicService.save(topic));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
    }

    @Operation(summary = "Disable a topics in database by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableTopic(@Parameter(description = "Id of Topic to be Disabled")
                                                 @PathVariable(value = "id") Long id){
        if (topicService.existById(id)) {
            topicService.disable(id);
            return ResponseEntity.noContent().eTag("Topic disabled successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
    }

    @Operation(summary = "Delete a topics in database by its id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTopic(@Parameter(description = "Id of Topic to be Deleted")
                                               @PathVariable(value = "id") Long id){
        if (topicService.existById(id)){
            topicService.delete(id);
            return  ResponseEntity.noContent().eTag("Topic deleted successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic not found.");
    }
}
