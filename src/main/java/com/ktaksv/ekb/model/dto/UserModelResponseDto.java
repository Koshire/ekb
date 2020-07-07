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
public class UserModelResponseDto implements Serializable {

    private String username;
    private String fio;
    private String position;
    private String department;
    private boolean enabled;
    private Set<String> authorities;
}
