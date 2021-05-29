package com.application.icafapi.model;

import lombok.*;
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
    private String email;
    private String name;
    private String contact;
    private String title;
    private String author;
    private String paperAbstract;
    private String fileName;
    private String status;
    private String paid;
    private String rComment;

}
