package com.app.identity.web;

import com.app.identity.service.UserService;
import com.app.identity.web.dto.UpdateUserRequest;
import com.app.identity.web.dto.UserRequest;
import com.app.identity.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserResponse> list(@RequestParam(name = "search", required = false) String search) {
        return userService.search(search);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> create(@RequestBody @Validated UserRequest request) {
        return userService.create(request);
    }

    @PatchMapping("/{id}")
    public Mono<UserResponse> update(@PathVariable UUID id, @RequestBody @Validated UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }
}
