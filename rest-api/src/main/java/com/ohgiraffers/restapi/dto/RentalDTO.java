package com.ohgiraffers.restapi.dto;

import com.ohgiraffers.restapi.enums.RentalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "대여 정보 DTO")
public class RentalDTO {

    @Schema(description = "대여 번호(PK)")
    private Integer rentalNo;
    @Schema(description = "회원 번호")
    private Integer memberNo;
    @Schema(description = "도서 번호")
    private Integer bookNo;
    @Schema(description = "대여 날짜")
    private LocalDate rentedAt;
    @Schema(description = "반납 기한")
    private LocalDate dueDate;
    @Schema(description = "반납 날짜")
    private LocalDate returnedAt;
    @Schema(description = "대여 상태")
    private RentalStatus status;

    public boolean isOverdue() {
        return status == RentalStatus.RENTED
                && dueDate != null
                && LocalDate.now().isAfter(dueDate);
    }
}