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

    private int memberNo;

    @NonNull
    @NotBlank
    @Size(min = 4, max = 20)
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Email
    private String email;
    private LocalDate joinedAt;
}
