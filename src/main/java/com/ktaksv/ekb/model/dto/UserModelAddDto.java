package com.ktaksv.ekb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModelAddDto implements Serializable {

    private String username;
    private String password;
    private String fio;
    private String position;
    private String department;
    private Set<String> authorities;
}
