package com.forumalura.controllers;

import com.forumalura.domain.answers.Answer;
import com.forumalura.domain.answers.AnswerCreateDTO;
import com.forumalura.domain.answers.AnswerUpdateDTO;
import com.forumalura.domain.topics.Topic;
import com.forumalura.domain.topics.TopicCreateDTO;
import com.forumalura.domain.topics.TopicUpdateDTO;
import com.forumalura.services.AnswerService;
import com.forumalura.services.AuthenticationFacade;
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
@RequestMapping("/api/answers")
@SecurityRequirement(name = "Forum")
@Tag(name = "Answers")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    TopicService topicService;
    @Autowired
    UserService userService;

    @Operation(summary = "Create a answers in Database")
    @PostMapping
    public ResponseEntity<Object> createAnswer(@RequestBody @Valid AnswerCreateDTO requestDTO){
        var author = userService.findByEmail(authenticationFacade.getEmail()).get();
        var topic = topicService.findById(requestDTO.topicId()).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.save(new Answer(requestDTO,topic,author)));
    }

    @Operation(summary = "Find all answers in Database")
    @GetMapping("/all")
    public ResponseEntity<Page<Answer>> getAllAnswer(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(answerService.findAll(pageable));
    }

    @Operation(summary = "Find all answers in Database where the user is the author")
    @GetMapping("/author")
    public ResponseEntity<Page<Answer>> getAllAnswerByAuthor(@ParameterObject Pageable pageable){
        var author = userService.findByEmail(authenticationFacade.getEmail()).get();
        return ResponseEntity.ok(answerService.findAllByAuthor(author,pageable));
    }

    @Operation(summary = "Find all answers in Database of a topic")
    @GetMapping("/topic/{id}")
    public ResponseEntity<Page<Answer>> getAllAnswerByTopic(@ParameterObject Pageable pageable,
                                                            @Parameter(description = "Id of Answer to be Searched")
                                                            @PathVariable(value = "id") Long id){
        var topic = topicService.findById(id).get();
        return ResponseEntity.ok(answerService.findAllByTopic(topic,pageable));
    }

    @Operation(summary = "Find all answers actives in Database")
    @GetMapping
    public ResponseEntity<Page<Answer>> getAllAnswerActive(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(answerService.findAllByActiveDateTrue(pageable));
    }

    @Operation(summary = "Find a answers in Database by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getByIdAnswer(@Parameter(description = "Id of Answer to be Searched")
                                               @PathVariable(value = "id") Long id){
        Optional<Answer> optional = answerService.findById(id);
        return optional.isPresent()
                ? ResponseEntity.ok(optional.get())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
    }

    @Operation(summary = "Update a answers in Database")
    @PutMapping
    public ResponseEntity<Object> updateAnswer(@RequestBody @Valid AnswerUpdateDTO requestDTO){
        if (answerService.existById(requestDTO.id())){
            var answer = answerService.findById(requestDTO.id()).get();
            answer.setMessage(requestDTO.message());
            return ResponseEntity.ok(answerService.save(answer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
    }

    @Operation(summary = "Disable a answers in database by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableAnswer(@Parameter(description = "Id of Answer to be Disabled")
                                               @PathVariable(value = "id") Long id){
        if (answerService.existById(id)) {
            answerService.disable(id);
            return ResponseEntity.noContent().eTag("Answer disabled successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
    }

    @Operation(summary = "Delete a answers in database by its id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAnswer(@Parameter(description = "Id of Answer to be Deleted")
                                              @PathVariable(value = "id") Long id){
        if (answerService.existById(id)){
            answerService.delete(id);
            return  ResponseEntity.noContent().eTag("Answer deleted successfully.").build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Answer not found.");
    }
}
