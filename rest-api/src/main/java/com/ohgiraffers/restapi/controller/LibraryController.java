package com.ohgiraffers.restapi.controller;

import com.ohgiraffers.restapi.dto.BookDTO;
import com.ohgiraffers.restapi.dto.MemberDTO;
import com.ohgiraffers.restapi.dto.ResponseMessage;
import com.ohgiraffers.restapi.enums.BookStatus;
import com.ohgiraffers.restapi.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/v1/library")
public class LibraryController {

    private final List<BookDTO> bookList;
    private final List<MemberDTO> memberList;

    public LibraryController() {
        this.bookList = new ArrayList<>();
        this.memberList = new ArrayList<>();

        bookList.add(new BookDTO(1, "짱구는 못말려", "신형만", "a123", BookStatus.AVAILABLE, LocalDate.of(2026, 5, 1)));
        bookList.add(new BookDTO(2, "자바의 정석", "봉미선", "b456", BookStatus.AVAILABLE, LocalDate.of(2017, 10, 11)));
        bookList.add(new BookDTO(3, "개구리중사 케로로", "도로로", "c887", BookStatus.AVAILABLE, LocalDate.of(2009, 3, 13)));
        bookList.add(new BookDTO(4, "원피스", "루피", "a774", BookStatus.AVAILABLE, LocalDate.of(1997, 11, 21)));
        bookList.add(new BookDTO(5, "명탐정 코난", "유명한", "b002", BookStatus.AVAILABLE, LocalDate.of(2002, 5, 5)));

        memberList.add(new MemberDTO(1, "user01", "짱구", "zzanggu@naver.com", LocalDate.of(2024, 4, 2)));
        memberList.add(new MemberDTO(2, "user02", "훈이", "hoonny@gmail.com", LocalDate.of(2026, 1, 10)));
        memberList.add(new MemberDTO(3, "user03", "철수", "cscs@naver.com", LocalDate.of(2020, 5, 17)));
        memberList.add(new MemberDTO(4, "user04", "맹구", "manggu@gmail.com", LocalDate.of(2018, 9, 10)));
        memberList.add(new MemberDTO(5, "user05", "유리", "yuriii@naver.com", LocalDate.of(2023, 11, 22)));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseMessage> findMemberByName(@RequestParam(required = false) String name) {

        List<MemberDTO> foundMembers = memberList.stream()
                .filter(m -> {
                    if (name != null && !m.getName().contains(name)) {
                        return false;
                    }
                    return true;
                }).toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("members", foundMembers);

        ResponseMessage responseMessage = new ResponseMessage(200, "회원 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<ResponseMessage> findMemberByMemberNo(@PathVariable int memberNo) {
        MemberDTO foundMember = memberList.stream()
                .filter(m -> m.getMemberNo() == memberNo)
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("찾으시는 회원번호가 존재하지 않습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("member", foundMember);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "회원 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }
//
//    @PostMapping("/members")
//    public ResponseEntity<ResponseMessage> createMember(@RequestBody MemberDTO member) {
//
//    }
}
