package com.app.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@Document(collection = "users")
public class User {
    
    @Id
    private String id;
    private String username;
    private String password;
    private boolean active;

}
