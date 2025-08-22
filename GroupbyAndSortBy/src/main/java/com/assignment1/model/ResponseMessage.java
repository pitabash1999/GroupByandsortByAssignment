package com.assignment1.model;

import lombok.Getter;

@Getter
public enum ResponseMessage {

    SUCCESS("Record added successfully"),
    FAILURE("Unable to save record");


    private final String message;

    ResponseMessage(String message){
        this.message = message;
    }
}
