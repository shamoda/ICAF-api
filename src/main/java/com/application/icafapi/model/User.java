package com.application.icafapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String phone;
    private String email;
    private String role;
    private String password;

}
