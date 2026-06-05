package com.ohgiraffers.restapi.dto;

import com.ohgiraffers.restapi.enums.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "도서 정보 DTO")
public class BookDTO {

    @Schema(description = "도서 번호(PK)")
    private Integer bookNo;
    @NotBlank(message = "도서 제목은 필수 입력 값입니다.")
    @Schema(description = "도서 제목")
    private String title;
    @NotBlank(message = "도서 저자는 필수 입력 값입니다.")
    @Schema(description = "도서 저자")
    private String author;
    @NotBlank(message = "도서 isbn은 필수 입력 값입니다.")
    @Schema(description = "도서 isbn")
    private String isbn;
    @Schema(description = "대여 가능 상태", example = "AVAILABLE")
    private BookStatus status;
    @Schema(description = "도서 출판일")
    @PastOrPresent(message = "출판일은 과거 또는 현재만 가능합니다.")
    private LocalDate publishedAt;
}
