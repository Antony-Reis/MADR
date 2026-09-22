package com.antony.madr.book;

import com.antony.madr.novelist.INovelistRepository;
import com.antony.madr.novelist.NovelistEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookService {
    private final IBookRepository ibookRepository;
    private final INovelistRepository iNovelistRepository;

    public BookService(IBookRepository ibookRepository, INovelistRepository iNovelistRepository) {
        this.ibookRepository = ibookRepository;
        this.iNovelistRepository = iNovelistRepository;
    }

    public void createBook(BookDto bookDto) throws BadRequestException {
        NovelistEntity novelist = iNovelistRepository.findById(bookDto.getNovelistId())
                .orElseThrow(() -> new BadRequestException("Novelist dont exits"));
        Set<BookEntity> bookEntity = novelist.getBooks();
         boolean bookExits = bookEntity.stream()
                 .anyMatch(book -> book.getTitle().equalsIgnoreCase(bookDto.getTitle()));

         if (bookExits){
             throw new BadRequestException("This novelist already has a book with this title.");
         }
         ibookRepository.save(new BookEntity(bookDto.getTitle(),bookDto.getYear(), novelist));
    }
    public void deleteBook(Integer id){
        ibookRepository.deleteById(id);
    }

}
