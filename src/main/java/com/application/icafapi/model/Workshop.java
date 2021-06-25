package com.application.icafapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document
public class Workshop {
    @Id
    private String workshopId;
    private String title;
    private String subject;
    private String conductor;
    private String description;
    private String fileName;
    private String imageName;
    private String venue;
    private String date;
    private String time;
    private String status;
    private String rComment;
    private String aComment;
    private LocalDateTime current;
    private String publish;
    private Boolean edit;
    private String postComment;
    private LocalDateTime editDate;
}
