package com.application.icafapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SmsRequest {  //SMS Request Model

    private String phoneNumber;
    private String message;
}
