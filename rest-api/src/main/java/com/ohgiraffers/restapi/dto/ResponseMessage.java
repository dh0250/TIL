package com.ohgiraffers.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseMessage {

    private Integer status;
    private String message;
    private Map<String, Object> results;
}
