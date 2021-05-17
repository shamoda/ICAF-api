package com.application.icafapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Document
public class Researcher {

    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String contact;
    private String password;
    private String title;
    private String author;
    private String paperAbstract;
    private String fileName;
    private boolean approved;
    private boolean paid;

}
