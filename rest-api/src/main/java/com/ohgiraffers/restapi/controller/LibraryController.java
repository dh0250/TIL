package com.ohgiraffers.restapi.controller;

import com.ohgiraffers.restapi.dto.BookDTO;
import com.ohgiraffers.restapi.dto.MemberDTO;
import com.ohgiraffers.restapi.dto.RentalDTO;
import com.ohgiraffers.restapi.dto.ResponseMessage;
import com.ohgiraffers.restapi.enums.BookStatus;
import com.ohgiraffers.restapi.enums.RentalStatus;
import com.ohgiraffers.restapi.exception.BookAlreadyRentedException;
import com.ohgiraffers.restapi.exception.BookNotFoundException;
import com.ohgiraffers.restapi.exception.MemberNotFoundException;
import com.ohgiraffers.restapi.exception.RentalNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Tag(name = "도서관 회원, 도서 대여 관련 api", description = "회원 조회, 등록, 도서 조회, 등록, 대여, 반납 api")
@RestController
@RequestMapping("api/v1/library")
public class LibraryController {

    private final List<BookDTO> bookList;
    private final List<MemberDTO> memberList;
    private final List<RentalDTO> rentalList;

    public LibraryController() {
        this.bookList = new ArrayList<>();
        this.memberList = new ArrayList<>();
        this.rentalList = new ArrayList<>();

        bookList.add(new BookDTO(1, "짱구는 못말려", "신형만", "111-21-1411", BookStatus.AVAILABLE, LocalDate.of(2026, 5, 1)));
        bookList.add(new BookDTO(2, "자바의 정석", "봉미선", "324-456-899", BookStatus.AVAILABLE, LocalDate.of(2017, 10, 11)));
        bookList.add(new BookDTO(3, "개구리중사 케로로", "도로로", "777-88-3511", BookStatus.AVAILABLE, LocalDate.of(2009, 3, 13)));
        bookList.add(new BookDTO(4, "원피스", "루피", "313-232-447-7", BookStatus.RENTED, LocalDate.of(1997, 11, 21)));
        bookList.add(new BookDTO(5, "명탐정 코난", "유명한", "121-11-255-66", BookStatus.RENTED, LocalDate.of(2002, 5, 5)));

        memberList.add(new MemberDTO(1, "user01", "짱구", "zzanggu@naver.com", LocalDate.of(2024, 4, 2)));
        memberList.add(new MemberDTO(2, "user02", "훈이", "hoonny@gmail.com", LocalDate.of(2026, 1, 10)));
        memberList.add(new MemberDTO(3, "user03", "철수", "cscs@naver.com", LocalDate.of(2020, 5, 17)));
        memberList.add(new MemberDTO(4, "user04", "맹구", "manggu@gmail.com", LocalDate.of(2018, 9, 10)));
        memberList.add(new MemberDTO(5, "user05", "유리", "yuriii@naver.com", LocalDate.of(2023, 11, 22)));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseMessage> getMembers(@RequestParam(required = false) String name) {

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
    public ResponseEntity<ResponseMessage> getMemberByMemberNo(@PathVariable int memberNo) {
        MemberDTO foundMember = memberList.stream()
                .filter(m -> m.getMemberNo() == memberNo)
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("찾으시는 회원번호가 존재하지 않습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("member", foundMember);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "회원 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberDTO member) {
        member.setMemberNo(memberList.size() + 1);
        member.setJoinedAt(LocalDate.now());
        memberList.add(member);

        return ResponseEntity.created(URI.create("/api/v1/library/members/" + member.getMemberNo())).build();
    }

    @GetMapping("/books")
    public ResponseEntity<ResponseMessage> getBooks(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String status) {

        List<BookDTO> foundBooks = bookList.stream()
                .filter(book -> title == null || book.getTitle().contains(title))
                .filter(book -> status == null || book.getStatus().equals(BookStatus.valueOf(status.toUpperCase())))
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("Books", foundBooks);

        ResponseMessage responseMessage = new ResponseMessage(200, "도서 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/books/{bookNo}")
    public ResponseEntity<ResponseMessage> getBookByBookNo(@PathVariable int bookNo) {
        BookDTO foundBook = bookList.stream()
                .filter(book -> book.getBookNo() == bookNo)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("찾으시는 도서번호가 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("book", foundBook);

        ResponseMessage responseMessage = new ResponseMessage(200, "도서 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/books")
    public ResponseEntity<Void> createBook(@Valid @RequestBody BookDTO book) {
        book.setBookNo(bookList.size() + 1);
        book.setStatus(BookStatus.AVAILABLE);

        bookList.add(book);

        return ResponseEntity.created(URI.create("/api/v1/library/books/" + book.getBookNo())).build();
    }

    @DeleteMapping("/books/{bookNo}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookNo) {
        BookDTO foundBook = bookList.stream()
                .filter(b -> b.getBookNo() == bookNo)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("도서 번호가 존재하지 않습니다."));

        foundBook.setStatus(BookStatus.DELETED);
        //bookList.remove(foundBook);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rentals")
    public ResponseEntity<ResponseMessage> getRentals(@RequestParam(required = false) Integer memberNo) {

        List<RentalDTO> rentalListByMember = this.rentalList.stream()
                .filter(r -> memberNo == null || r.getMemberNo().equals(memberNo))
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rentals", rentalListByMember);

        ResponseMessage responseMessage = new ResponseMessage(200, "대여 도서 목록 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/rentals")
    public ResponseEntity<Void> rentBook(@Valid @RequestBody RentalDTO rental) {

        memberList.stream()
                .filter(m -> m.getMemberNo() == rental.getMemberNo())
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("회원이 아닙니다."));

        BookDTO rentalBook = bookList.stream()
                .filter(b -> rental.getBookNo() == b.getBookNo())
                .filter(b -> {
                    if (b.getStatus().equals(BookStatus.RENTED)) {
                        throw new BookAlreadyRentedException("대여중인 도서입니다.");
                    }

                    if (b.getStatus().equals(BookStatus.DELETED)) {
                        throw new BookNotFoundException("삭제된 도서입니다.");
                    }

                    return true;
                })
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("찾으시는 도서가 없습니다."));

        rentalBook.setStatus(BookStatus.RENTED);
        rental.setRentalNo(rentalList.size() + 1);
        rental.setRentedAt(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(14));
        rental.setStatus(RentalStatus.RENTED);
        rentalList.add(rental);

        return ResponseEntity.created(URI.create("/api/v1/library/rentals/" + rental.getRentalNo())).build();
    }

    @GetMapping("/rentals/{rentalNo}")
    public ResponseEntity<ResponseMessage> getRentalBook(@PathVariable int rentalNo) {
        RentalDTO foundRentalBook = rentalList.stream()
                .filter(r -> r.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException("대여 정보가 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rental", foundRentalBook);

        ResponseMessage responseMessage = new ResponseMessage(200, "대여 도서 조회 성공", response);

        return ResponseEntity.ok(responseMessage);
    }

    @PatchMapping("/rentals/{rentalNo}/return")
    public ResponseEntity<Void> returnBook(@PathVariable int rentalNo) {
        RentalDTO rental = rentalList.stream()
                .filter(r -> r.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException("대여 번호가 존재하지 않습니다."));

        BookDTO returnedBook = bookList.stream()
                .filter(b -> b.getBookNo() == rental.getBookNo())
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("찾으시는 도서가 존재하지 않습니다"));

        returnedBook.setStatus(BookStatus.AVAILABLE);

        if (rental.getStatus().equals(RentalStatus.RETURNED)) {
            throw new IllegalArgumentException("이미 반납된 도서입니다.");
        }
        rental.setStatus(RentalStatus.RETURNED);
        rental.setReturnedAt(LocalDate.now());


        return ResponseEntity.noContent().build();
    }
}
