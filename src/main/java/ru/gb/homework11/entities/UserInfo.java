package ru.gb.homework11.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String name;
    private String roles;
    private String email;
    private String password;
}
