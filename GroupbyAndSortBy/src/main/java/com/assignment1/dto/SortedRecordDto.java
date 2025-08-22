package com.assignment1.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SortedRecordDto {
	
	private List<GroupedRecords> sortedRecords;
}
