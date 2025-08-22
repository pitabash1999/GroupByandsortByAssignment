package com.assignment1.repository;

import com.assignment1.model.Record;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {
	
	List<Record> findByDepartment(String department);
	List<Record> findByAge(Integer age);
	List<Record> findByName(String name);
	List<Record> findByDatasetId(Long datasetId);
	
}
