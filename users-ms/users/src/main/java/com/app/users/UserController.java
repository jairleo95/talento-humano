package com.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Flux<User> getAll(){
        return userService.findAll();
    }

    @PostMapping
    public Mono<User> save(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/{id}")
    private Mono<ResponseEntity<User>> update(@PathVariable("id") String id, @RequestBody User user) {
        return this.userService.update(id, user)
           .flatMap(x -> Mono.just(ResponseEntity.ok(x)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

	@DeleteMapping("/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable("id") String id) {
        return this.userService.delete(id)
           .flatMap(customer -> Mono.just(ResponseEntity.ok("Deleted Successfully")))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/findByUsername")
	public Flux<User> findByUsername(@RequestParam String name) {
		return userService.findByUsername(name);
	}

    
}
