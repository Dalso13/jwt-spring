package com.almond.jwt_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String email;
    private Timestamp createDate;


    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return List.of(this.role.split(","));
        }
        return List.of();
    }
}
