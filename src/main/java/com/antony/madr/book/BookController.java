package com.antony.madr.book;

import com.antony.madr.infra.exceptions.ConflictException;
import com.antony.madr.infra.exceptions.NotFoundException;
import com.antony.madr.utils.RDefaultResponse;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
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
    public RDefaultResponse postBook(@RequestBody @Valid RBookDto body) throws NotFoundException, ConflictException {
        return bookService.createBook(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse deleteBook(@PathVariable Integer id) throws NotFoundException{
        return bookService.deleteBook(id);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RBookResponseDto getBookById(@PathVariable Integer id) throws NotFoundException {return bookService.getBookById(id);}




    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<RBookResponseDto> searchByTitleAndYear(@RequestParam(defaultValue = "10") Integer size,
                                                 @RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam String title,
                                                 @RequestParam Integer year)  {
        return bookService.listBookByNameAndYear(page, size, title, year);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse patchBookById(@PathVariable Integer id,
                                          @RequestBody @Valid RBookDto body) throws NotFoundException {
        return bookService.patchBookById(id,body);
    }

}
