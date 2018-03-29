package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UNIVERSITY")
public class UniversityEntity {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "NUMBEROFSTUDENTS")
    private int numberOfStudents;

    @JsonIgnore
    @Column(name = "SEMESTERCOSTS")
    private int semesterCosts;

    public UniversityEntity(String name, int numberOfStudents, int semesterCosts){
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.semesterCosts = semesterCosts;
    }

    public UniversityEntity(){}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNumberOfStudents() { return numberOfStudents; }
    public void setNumberOfStudents(int numberOfStudents) { this.numberOfStudents = numberOfStudents; }
    public int getSemesterCosts() { return semesterCosts; }
    public void setSemesterCosts(int semesterCosts ) { this.semesterCosts = semesterCosts; }
}
