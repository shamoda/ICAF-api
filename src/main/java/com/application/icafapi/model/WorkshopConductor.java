package com.application.icafapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document
public class WorkshopConductor {

    @Id
    private String email;
    private String name;
    private String phone;
    private String workshopTitle;
    private String eduQualifications;
    private String organization;
    private String post;
    private boolean accept; //Acceptance of Workshop proposal
}
