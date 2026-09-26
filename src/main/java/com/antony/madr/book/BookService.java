package com.antony.madr.book;

import com.antony.madr.infra.exceptions.ConflictException;
import com.antony.madr.infra.exceptions.EExceptionsTypes;
import com.antony.madr.infra.exceptions.NotFoundException;
import com.antony.madr.utils.RDefaultResponse;
import com.antony.madr.novelist.INovelistRepository;
import com.antony.madr.novelist.NovelistEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Locale;
import java.util.Set;

@Service
public class BookService {
    private final IBookRepository ibookRepository;
    private final INovelistRepository iNovelistRepository;

    public BookService(IBookRepository ibookRepository, INovelistRepository iNovelistRepository) {
        this.ibookRepository = ibookRepository;
        this.iNovelistRepository = iNovelistRepository;
    }

    public RDefaultResponse createBook(RBookDto bookDto) throws ConflictException, NotFoundException {
        Integer yearNow = Year.now().getValue();

        if (bookDto.year() > yearNow){
            bookDto = new RBookDto(bookDto.novelistId(), bookDto.title().trim().toLowerCase(), yearNow);
        }

        NovelistEntity novelist = iNovelistRepository.findById(bookDto.novelistId())
                .orElseThrow(() -> new NotFoundException(EExceptionsTypes.Novelist));
        Set<BookEntity> bookEntity = novelist.getBooks();

        RBookDto finalBookDto = bookDto;
        boolean bookExits = bookEntity.stream()
                 .anyMatch(book -> book.getTitle().equalsIgnoreCase(finalBookDto.title()));

         if (bookExits){
             throw new ConflictException(EExceptionsTypes.Book);
         }
         ibookRepository.save(new BookEntity(bookDto.title(),bookDto.year(), novelist));

         return new RDefaultResponse("Book created");
    }
    public RDefaultResponse deleteBook(Integer id) throws NotFoundException{
        BookEntity book = ibookRepository.findById(id).orElseThrow(() -> new NotFoundException(EExceptionsTypes.Book));

        ibookRepository.deleteById(book.getId());
        return new RDefaultResponse("Book deleted");
    }

    public RBookResponseDto getBookById(Integer id) throws NotFoundException {
        BookEntity book = ibookRepository.findById(id).orElse(null);
        if (book == null){
            throw new NotFoundException(EExceptionsTypes.Book);
        }
        return new RBookResponseDto(book);
    }

    public Page<RBookResponseDto> listBookByNameAndYear(Integer page, Integer size, String title, Integer year) {
        Integer yearNow = Year.now().getValue();
        if (year> yearNow) {
        year = yearNow;
        }
        Pageable pageable;
        if (size < 20){
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        }
        Page<BookEntity> books = ibookRepository.findByTitleContainingAndYear(title,year, pageable);

        return books.map(RBookResponseDto::new);
    }

    public RDefaultResponse patchBookById(Integer id, RBookDto bookDto) throws NotFoundException {
        BookEntity book = ibookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NotFoundException(EExceptionsTypes.Book);
        }

        if (bookDto.title() != null) {
            book.setTitle(bookDto.title());
        }
        if (bookDto.year() != null) {
            book.setYear(bookDto.year());
        }
        if (bookDto.novelistId() != null) {
            book.setNovelist(iNovelistRepository.findById(bookDto.novelistId()).orElse(null));
        }

        ibookRepository.save(book);

        return new RDefaultResponse("Book updated");
    }

}
