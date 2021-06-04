package com.application.icafapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document
public class WorkshopConductor {
    @Id
    private String email;
    private String fName;
    private String lName;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String eduQualification1;
    private String eduQualification2;
    private String organization;
    private String post;
}
