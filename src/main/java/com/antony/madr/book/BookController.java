package com.antony.madr.book;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/books")
@Validated
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void postBook(@RequestBody @Valid BookDto body) throws BadRequestException {
        bookService.createBook(body);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
    }
}
