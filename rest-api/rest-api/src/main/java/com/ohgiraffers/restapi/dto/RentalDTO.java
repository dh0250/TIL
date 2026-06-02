package com.ohgiraffers.restapi.dto;

import com.ohgiraffers.restapi.enums.RentalStatus;
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
public class RentalDTO {

    private int rentalNo;
    private int memberNo;
    private int bookNo;
    private LocalDate dueDate;
    private Local returnedAt;
    private RentalStatus status;
}
