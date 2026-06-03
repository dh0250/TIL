package com.ohgiraffers.restapi.dto;

import com.ohgiraffers.restapi.enums.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Integer bookNo;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;
    private BookStatus status;
    @PastOrPresent(message = "가입일은 현재보다 과거 날짜가 입력 되어야 합니다.")
    private LocalDate publishedAt;
}
