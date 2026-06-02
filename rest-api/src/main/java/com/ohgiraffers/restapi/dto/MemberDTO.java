package com.ohgiraffers.restapi.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDTO {

    private int memberNo;
    private String id;
    private String name;
    private String email;
    private LocalDate joinedAt;
}
