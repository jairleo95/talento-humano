package com.app.users;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> save(User user);

    Mono<User> delete(String id);

    Mono<User> update(String id, User user);

    Flux<User> findAll();

    Mono<User> findById(String id);
    
    Flux<User> findByUsername(String name);
}
