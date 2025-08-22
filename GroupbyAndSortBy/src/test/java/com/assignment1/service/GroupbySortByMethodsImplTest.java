package com.assignment1.service;

import com.assignment1.dto.GroupByResponseDto;
import com.assignment1.dto.SaveRecordRequest;
import com.assignment1.dto.SavedResponseDto;
import com.assignment1.dto.SortedRecordDto;
import com.assignment1.exceptions.EmptyDataSetException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupbySortByMethodsImplTest {
	
	@Autowired
	private GroupbySortByMethodsImpl method1;

	
    //Test for not null result
    @Test
    public void saveRecordTest() throws NoResourceFoundException{
        
        SaveRecordRequest saveRecordRequest=new SaveRecordRequest();
        saveRecordRequest.setName("Pitabash");
        saveRecordRequest.setAge(20);
        saveRecordRequest.setDepartment("Engineering");
        SavedResponseDto responseDto=method1.saveRecord("employee_dataset",saveRecordRequest);
        assertNotNull(responseDto);
    }
    
    @Test
    public void saveRecordTest_WithoutDataset(){
        
        SaveRecordRequest saveRecordRequest=new SaveRecordRequest();
        saveRecordRequest.setName("Pitabash");
        saveRecordRequest.setAge(20);
        saveRecordRequest.setDepartment("Engineering");
        assertThrows(RuntimeException.class, ()->{
        	method1.saveRecord(null,saveRecordRequest);
        });
    }
    
    @Test
    public void saveRecordTest_WithEmptyDataset(){
        
        SaveRecordRequest saveRecordRequest=new SaveRecordRequest();
        saveRecordRequest.setName("Pitabash");
        saveRecordRequest.setAge(20);
        saveRecordRequest.setDepartment("Engineering");
        assertThrows(EmptyDataSetException.class, ()->{
        	method1.saveRecord("",saveRecordRequest);
        });
    }

    @Test
     void groupByTest(){

        String dataset="Mechanical";
        String groupBy="department";

        GroupByResponseDto groupByResponseDto=method1.getGroupedRecords(dataset,groupBy);
        assertNotNull(groupByResponseDto);
    }

    @Test
    void groupByTest_nullInputs(){

        String dataset="";
        String groupBy="";
        assertThrows(NullPointerException.class,()->{
            GroupByResponseDto groupByResponseDto=method1.getGroupedRecords(dataset,groupBy);
        });

    }

    @Test
    void getSortedRecordsTest() throws NoResourceFoundException {

        SaveRecordRequest saveRecordRequest=new SaveRecordRequest();
        saveRecordRequest.setName("Pitabash");
        saveRecordRequest.setAge(20);
        saveRecordRequest.setDepartment("Engineering");
        SavedResponseDto responseDto=method1.saveRecord("employee_dataset",saveRecordRequest);

        String dataset="employee_dataset";
        String orderBy="desc";
        String sortBy="age";

        SortedRecordDto sortedRecordDto=method1.getSortedRecords(dataset,sortBy,orderBy);
        assertNotNull(sortedRecordDto);
    }

    @Test
    void getSortedRecordsTest_nullValues() throws NoResourceFoundException {

        SaveRecordRequest saveRecordRequest=new SaveRecordRequest();
        saveRecordRequest.setName("Pitabash");
        saveRecordRequest.setAge(20);
        saveRecordRequest.setDepartment("Engineering");
        SavedResponseDto responseDto=method1.saveRecord("employee_dataset",saveRecordRequest);

        String dataset="";
        String orderBy="";
        String sortBy="";


        assertThrows(NoSuchElementException.class,()->{
            SortedRecordDto sortedRecordDto=method1.getSortedRecords(dataset,sortBy,orderBy);
        });
    }

	
    
    

}