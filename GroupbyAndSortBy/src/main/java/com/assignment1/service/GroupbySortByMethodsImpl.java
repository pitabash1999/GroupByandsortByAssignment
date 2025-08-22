package com.assignment1.service;




import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.assignment1.dto.GroupByResponseDto;
import com.assignment1.dto.GroupedRecords;
import com.assignment1.dto.SaveRecordRequest;
import com.assignment1.dto.SavedResponseDto;
import com.assignment1.dto.SortedRecordDto;
import com.assignment1.exceptions.EmptyDataSetException;
import com.assignment1.model.Dataset;
import com.assignment1.model.Record;
import com.assignment1.model.ResponseMessage;
import com.assignment1.repository.DatasetRepository;
import com.assignment1.repository.RecordRepository;
import com.assignment1.service.interfaces.GroupbySortByMethods;



@Service
public class GroupbySortByMethodsImpl implements GroupbySortByMethods {
	
	@Autowired
	private DatasetRepository datasetRepository;
	@Autowired
	private RecordRepository recordRepository;
	
	
    @Override
    public SavedResponseDto saveRecord(String dataset, SaveRecordRequest request)throws NoResourceFoundException {



    	if(dataset.isEmpty()) {
    		throw new EmptyDataSetException("Dataset can not be a empty");
    	}
    	
    	try {
    		
    		Optional<Dataset> existingDataset=datasetRepository.findBydatasetName(dataset);
        	
        	if(existingDataset.isPresent()) {
        		Record record=new Record();
        		record.setName(request.getName());
        		record.setAge(request.getAge());
        		record.setDepartment(request.getDepartment());
        		record.setDataset(existingDataset.get());
        		Record savedRecord=recordRepository.save(record);
        		
        		return responseDto(savedRecord.getId(), dataset, ResponseMessage.SUCCESS.getMessage());
        	}else {
        		Dataset newDataset=new Dataset();
        		newDataset.setDatasetName(dataset);
        		Dataset savedDataset=datasetRepository.save(newDataset);
        		Record record=new Record();
        		record.setName(request.getName());
        		record.setAge(request.getAge());
        		record.setDepartment(request.getDepartment());;
        		record.setDataset(savedDataset);
        		Record savedRecord=recordRepository.save(record);
        		
        		return responseDto(savedRecord.getId(), dataset, ResponseMessage.SUCCESS.getMessage());
        	}
			
		} catch (RuntimeException e) {
			throw new RuntimeException("Server error");
		}
    	
    	
    }
    
    
    
    
    private SavedResponseDto responseDto(Long recordId,String datasetName,String message) {
    	
    	return SavedResponseDto.builder()
    			.message(message)
    			.dataset(datasetName)
    			.recordId(recordId)
    			.build();
    	
    }


	@Override
	public GroupByResponseDto getGroupedRecords(String datasetName, String groupByParam) {

		if(datasetName==null || groupByParam==null || datasetName.isBlank() || groupByParam.isBlank()){
			throw new NullPointerException("Invalid inputs");
		}

		Map<String,List<GroupedRecords>> result=new HashMap<>();
		if(groupByParam.equalsIgnoreCase("age")) {
			
			List<Integer> ages= datasetRepository.findByAgeDatasetName(datasetName);
			
			
			for(Integer age:ages) {
				
				List<Record> records=recordRepository.findByAge(age);
				List<GroupedRecords> groupedRecords=records.stream()
						.map(record-> convertToGroupedRecordDto(record))
						.toList();
				result.put(String.valueOf(age), groupedRecords);
			}
			
			
			
			
		}else if(groupByParam.equalsIgnoreCase("department")) {
			
			List<String> departments= datasetRepository.findByDepartmentDatasetName(datasetName);
			
			
			for(String department:departments) {
				
				List<Record> records=recordRepository.findByDepartment(department);
				List<GroupedRecords> groupedRecords=records.stream()
						.map(record-> convertToGroupedRecordDto(record))
						.toList();
				result.put(department, groupedRecords);
			}
			
			
			
			
		}else if(groupByParam.equalsIgnoreCase("name")) {
			
			List<String> names= datasetRepository.findBynamesDatasetName(datasetName);
			
			
			for(String name:names) {
				
				List<Record> records=recordRepository.findByName(name);
				List<GroupedRecords> groupedRecords=records.stream()
						.map(record-> convertToGroupedRecordDto(record))
						.toList();
				result.put(name, groupedRecords);
			}
			
			
			
			
		}
		
		return new GroupByResponseDto(result);
	}
	
	private GroupedRecords convertToGroupedRecordDto(Record record) {
		
		return GroupedRecords.builder()
				.id(record.getId())
				.name(record.getName())
				.age(record.getAge())
				.department(record.getDepartment())
				.build();
	}




	@Override
	public SortedRecordDto getSortedRecords(String datasetName, String sortedByParam, String orderByParam) {
		
	    Dataset dataset= datasetRepository.findBydatasetName(datasetName)
				.orElseThrow(() -> new NoSuchElementException("Dataset not found with name: " + datasetName));
	    List<Record> records = recordRepository.findByDatasetId(dataset.getId());

	    // Create comparator based on sortedByParam
	    Comparator<Record> comparator = getComparator(sortedByParam);

	    // Apply ordering (ascending/descending)
	    if ("desc".equalsIgnoreCase(orderByParam)) {
	        comparator = comparator.reversed();
	    }

	    // Sort the records
	    List<GroupedRecords> sortedRecords = records.stream()
	            .sorted(comparator)
	            .map(this::convertToGroupedRecordDto)
	            .collect(Collectors.toList());

	    return new SortedRecordDto(sortedRecords);

	}

	private Comparator<Record> getComparator(String sortedByParam) {
	    switch (sortedByParam.toLowerCase()) {
	        case "age":
	            return Comparator.comparing(Record::getAge);
	        case "name":
	            return Comparator.comparing(Record::getName);
	        case "department":
	            return Comparator.comparing(Record::getDepartment);
	        case "id":
	            return Comparator.comparing(Record::getId);
	        default:
	            throw new IllegalArgumentException("Invalid sort parameter: " + sortedByParam);
	    }
	}

	
}
