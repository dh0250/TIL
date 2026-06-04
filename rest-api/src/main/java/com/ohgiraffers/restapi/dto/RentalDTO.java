package com.ohgiraffers.restapi.dto;

import com.ohgiraffers.restapi.enums.BookStatus;
import com.ohgiraffers.restapi.enums.RentalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "대여 정보 DTO")
public class RentalDTO {

    @Schema(description = "대여정보(PK)")
    private Integer rentalNo;
    @NotNull
    private Integer memberNo;
    @NotNull
    private Integer bookNo;
    private LocalDate rentedAt;
    private LocalDate dueDate;
    private LocalDate returnedAt;
    private RentalStatus status;

    public boolean isOverdue() {
        return status == RentalStatus.RENTED
                && dueDate != null
                && LocalDate.now().isAfter(dueDate);
    }
}