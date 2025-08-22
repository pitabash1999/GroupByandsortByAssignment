package com.assignment1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataset_id",nullable = false)
    private Dataset dataset;

}
