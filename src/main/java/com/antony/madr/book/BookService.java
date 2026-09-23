package com.antony.madr.book;

import com.antony.madr.assets.RDefaultResponse;
import com.antony.madr.novelist.INovelistRepository;
import com.antony.madr.novelist.NovelistEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Set;

@Service
public class BookService {
    private final IBookRepository ibookRepository;
    private final INovelistRepository iNovelistRepository;

    public BookService(IBookRepository ibookRepository, INovelistRepository iNovelistRepository) {
        this.ibookRepository = ibookRepository;
        this.iNovelistRepository = iNovelistRepository;
    }

    public RDefaultResponse createBook(RBookDto bookDto) throws BadRequestException {
        Integer year = Year.now().getValue();

        if (bookDto.year() > year){
            throw new BadRequestException("Book year inst valid");
        }

        NovelistEntity novelist = iNovelistRepository.findById(bookDto.novelistId())
                .orElseThrow(() -> new BadRequestException("Novelist dont exits"));
        Set<BookEntity> bookEntity = novelist.getBooks();
         boolean bookExits = bookEntity.stream()
                 .anyMatch(book -> book.getTitle().equalsIgnoreCase(bookDto.title()));

         if (bookExits){
             throw new BadRequestException("This novelist already has a book with this title.");
         }
         ibookRepository.save(new BookEntity(bookDto.title(),bookDto.year(), novelist));

         return new RDefaultResponse("Book created");
    }
    public RDefaultResponse deleteBook(Integer id){
        ibookRepository.deleteById(id);
        return new RDefaultResponse("Book deleted");
    }

    public RBookResponseDto getBookById(Integer id) throws BadRequestException {
        BookEntity book = ibookRepository.findById(id).orElse(null);
        if (book == null){
            throw new BadRequestException("Book dont exists");
        }
        return new RBookResponseDto(book);
    }

    public Page<RBookResponseDto> listBookByNameAndYear(Integer page, Integer size, String title, Integer year) throws BadRequestException {
        Integer yearNow = Year.now().getValue();
        if (year> yearNow) {
        throw new BadRequestException("Year inst valid");
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

    public RDefaultResponse patchBookById(Integer id, RBookDto bookDto) throws BadRequestException {
        BookEntity book = ibookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new BadRequestException("Book dont exists");
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
