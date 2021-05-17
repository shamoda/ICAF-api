package com.application.icafapi.model;

import lombok.*;
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
    private String email;
    private String name;
    private String contact;
    private String role;
    private String password;

}
