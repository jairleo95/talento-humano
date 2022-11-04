package com.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository repository;

    @Override
    public Mono<User> save(User user) {
        return this.repository.save(user);
    }

    @Override
    public Mono<User> delete(String id) {
        return this.repository.findById(id)
                    .flatMap(x-> this.repository.deleteById(x.getId())
                            .thenReturn(x)
                            );
    }

    @Override
    public Mono<User> update(String id, User user) {
        return this.repository.findById(id).flatMap(u-> {
            user.setId(id);
            return save(user);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Flux<User> findByUsername(String name) {
        return this.repository.findByUsername(name);
    }

    @Override
    public Mono<User> findById(String id) {
        return this.repository.findById(id).switchIfEmpty(Mono.empty());
    }
}
