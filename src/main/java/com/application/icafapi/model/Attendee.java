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
public class Attendee {

    @Id
    private String email;
    private String name;
    private String contact;
    private String organization;
    private String address;

}
