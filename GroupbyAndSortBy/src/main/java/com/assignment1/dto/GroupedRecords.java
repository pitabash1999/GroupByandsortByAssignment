package com.assignment1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupedRecords {
	
	private Long id;
	private String name;
	private Integer age;
	private String department;

}
