package com.assignment1.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavedResponseDto {

    private String message;
    private String dataset;
    private Long recordId;
}
