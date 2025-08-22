package com.assignment1.controller;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.assignment1.dto.SaveRecordRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupBySortByCotrollerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@Test
	public void saveRecordTest() throws JsonProcessingException, Exception{
		
		String dataSetName="employee";
		SaveRecordRequest svRecordRequest=new SaveRecordRequest();
		svRecordRequest.setName("Pitabash");
		svRecordRequest.setAge(29);
		svRecordRequest.setDepartment("Engieener");
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/dataset/"+dataSetName+"/record")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(svRecordRequest))
				)
		.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dataset").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recordId").exists());
		
	}
	
	@Test
	public void saveRecordTest_emptyDataset() throws Exception{
		
		String dataSetName=null;// dataset=""
		SaveRecordRequest svRecordRequest=new SaveRecordRequest();
		svRecordRequest.setName("Pitabash");
		svRecordRequest.setAge(29);
		svRecordRequest.setDepartment("Engieener");
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/dataset/"+dataSetName+"/record")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(svRecordRequest))
				)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void saveRecordTest_emptyRecord() throws JsonProcessingException, Exception{
		
		String dataSetName="employee";
		SaveRecordRequest svRecordRequest=new SaveRecordRequest();
		svRecordRequest.setName("Pitabash");
		svRecordRequest.setAge(29);
		svRecordRequest.setDepartment("Engieener");
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/dataset/"+dataSetName+"/record")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(null))
				)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}

	//Both groupBy and sortBy can not be done
	@Test
	void getRecordsTest() throws Exception {

		String dataset="employee_dataset";
		String groupBy="department";
		String sortBy="age";
		String orderBy="desc";

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dataset/"+dataset+"/query")
				.queryParam("groupBy",groupBy)
				.queryParam("sortBy",sortBy)
				.queryParam("orderBy",orderBy)
		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void getRecordsTest_sortBy() throws Exception {

		String dataSetName="employee_dataset";
		SaveRecordRequest svRecordRequest=new SaveRecordRequest();
		svRecordRequest.setName("Pitabash");
		svRecordRequest.setAge(29);
		svRecordRequest.setDepartment("Engieener");

		mockMvc.perform(
						MockMvcRequestBuilders.post("/api/dataset/"+dataSetName+"/record")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(svRecordRequest))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dataset").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recordId").exists());


		String dataset="employee_dataset";
		String sortBy="age";
		String orderBy="desc";

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dataset/"+dataset+"/query")
				.queryParam("sortBy",sortBy)
				.queryParam("orderBy",orderBy)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getRecordsTest_groupBy() throws Exception {

		String dataset="employee_dataset";
		String groupBy="department";

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dataset/"+dataset+"/query")
				.queryParam("groupBy",groupBy)

		).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getRecordsTest_sortByInvalidDataset() throws Exception {

		String dataSetName="employee";
		SaveRecordRequest svRecordRequest=new SaveRecordRequest();
		svRecordRequest.setName("Pitabash");
		svRecordRequest.setAge(29);
		svRecordRequest.setDepartment("Engieener");

		mockMvc.perform(
						MockMvcRequestBuilders.post("/api/dataset/"+dataSetName+"/record")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(svRecordRequest))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dataset").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.recordId").exists());
		String dataset="employee_dataset";
		String sortBy="age";
		String orderBy="desc";

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dataset/"+dataset+"/query")
				.queryParam("sortBy",sortBy)
				.queryParam("orderBy",orderBy)
		).andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}



	@Test
	void getRecordsTest_sortByWithoutParams() throws Exception {

		String dataset="employee_dataset";

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dataset/"+dataset+"/query")

		).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
