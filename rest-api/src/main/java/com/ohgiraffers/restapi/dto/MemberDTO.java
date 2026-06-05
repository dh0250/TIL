package com.ohgiraffers.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "회원 정보 DTO")
public class MemberDTO {

    @Schema(description = "회원정보(PK)")
    private Integer memberNo;

    @Schema(description = "회원 아이디")
    @NotBlank(message = "회원 아이디는 필수 입력 값입니다.")
    @Size(min = 4, max = 20, message = "4~20자 사이로 입력해주세요.")
    private String id;

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름은 반드시 입력해주세요.")
    private String name;

    @Schema(description = "회원 이메일")
    @NotBlank(message = "이메일은 반드시 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "가입 날짜")
    private LocalDate joinedAt;
}
