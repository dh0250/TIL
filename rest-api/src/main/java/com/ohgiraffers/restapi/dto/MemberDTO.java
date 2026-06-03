package com.ohgiraffers.restapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDTO {

    private Integer memberNo;

    @NotBlank
    @Size(min = 4, max = 20)
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
    private LocalDate joinedAt;
}
