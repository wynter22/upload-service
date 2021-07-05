package com.example.webapp.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class Member {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    public Member(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Member() {

    }
}
