package com.ktaksv.ekb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModelTokenDto implements Serializable {

    private String fio;
    private String position;
    private String department;
}
