package com.example.demo.Entities;

import javax.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private long ID;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private UniversityEntity university;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getID() { return ID; }
    public void setUniversity(UniversityEntity university) { this.university = university; }
    public UniversityEntity getUniversity() { return university; }
}