package com.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService{

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository repository;

    @Override
    public Mono<User> save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()
                && !user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return this.repository.save(user).map(this::sanitize);
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
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(u.getPassword());
            }
            user.setId(id);
            return save(user);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<User> findAll() {
        return this.repository.findAll().map(this::sanitize);
    }

    @Override
    public Flux<User> findByUsername(String name) {
        return this.repository.findByUsername(name).map(this::sanitize);
    }

    @Override
    public Mono<User> findById(String id) {
        return this.repository.findById(id).map(this::sanitize).switchIfEmpty(Mono.empty());
    }

    private User sanitize(User user) {
        user.setPassword(null);
        return user;
    }
}
