package com.forumalura.controllers;

import com.forumalura.domain.users.User;
import com.forumalura.domain.users.UserCreateDTO;
import com.forumalura.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Forum")
@Tag(name = "Users")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a user in Database")
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCreateDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(new User(requestDTO)));
    }
    @Operation(summary = "Find all user in Database")
    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUser(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(userService.findAll(pageable));
    }
    @Operation(summary = "Find all user in Database")
    @GetMapping
    public ResponseEntity<Page<User>> getAllActiveUser(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(userService.findAllActive(pageable));
    }
    @Operation(summary = "Find a user in Database by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getByIdUser(@Parameter(description = "Id of User to be Searched")
                                              @PathVariable(value = "id") Long id){
        Optional<User> optional = userService.findByUserId(id);
        return optional.isPresent()
                ? ResponseEntity.ok(optional.get())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
    @Operation(summary = "Find a product in Database by Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getByLoginUser(@Parameter(description = "Email of user to be Searched")
                                                 @PathVariable(value = "email") String email){
        Optional<User> optional = userService.findByEmail(email);
        return optional.isPresent()
                ? ResponseEntity.ok(optional.get())
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @Operation(summary = "Disable a Product in Database by its Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> disableUser (@Parameter(description = "Id of Product to be Disabled")
                                              @PathVariable(value = "id") Long id){
        Optional<User> optional = userService.findByUserId(id);
        if (optional.isPresent()) userService.disable(id);
        return optional.isPresent()
                ? ResponseEntity.noContent().eTag("User disabled successfully.").build()
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
    @Operation(summary = "Delete a Product in Database by its Id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser (@Parameter(description = "Id of Product to be Deleted")
                                              @PathVariable(value = "id") Long id){
        Optional<User> optional = userService.findByUserId(id);
        if (optional.isPresent()) userService.delete(id);
        return optional.isPresent()
                ? ResponseEntity.noContent().eTag("User deleted successfully.").build()
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
}
