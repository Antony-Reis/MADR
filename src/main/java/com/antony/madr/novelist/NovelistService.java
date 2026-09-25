package com.antony.madr.novelist;

import com.antony.madr.utils.RDefaultResponse;
import com.antony.madr.book.BookEntity;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NovelistService {
    private final INovelistRepository novelistRepository;

    public NovelistService(INovelistRepository novelistRepository) {
        this.novelistRepository = novelistRepository;
    }

    public RNovelistResponseDto getPerId(Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new BadRequestException("Novelsit dont exists");
        }
        return new RNovelistResponseDto(novelist);
    }

    public Page<RNovelistResponseDto> listPerName(String name,Integer page, Integer size){
        Pageable pageable;
        if (size < 20){
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        }

        Page<NovelistEntity> novelists = novelistRepository.findByNameContaining(name, pageable);
        return novelists.map(RNovelistResponseDto::new);
    }

    public RDefaultResponse createNovelist(RNovelistDto RNovelistDto) throws BadRequestException {
        String nameNovelistTrimLower = RNovelistDto.name().trim().toLowerCase();
        RNovelistDto = new RNovelistDto(nameNovelistTrimLower);

        NovelistEntity novelist = novelistRepository.findByName(RNovelistDto.name()).orElse(null);

        if (novelist != null) {
            throw new BadRequestException("Novelist already exists!");
        }
        novelistRepository.save(new NovelistEntity(RNovelistDto.name()));

        return new RDefaultResponse("Novelist create");
    }

    public RDefaultResponse deleteNovelist(Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new BadRequestException("Novelist dont exists");
        }
        for (BookEntity book : novelist.getBooks()){
            book.setNovelist(null);
        }
        novelist.getBooks().clear();

        novelistRepository.delete(novelist);
        return new RDefaultResponse("Novelist deleted");
    }

    public RDefaultResponse patchNovelist(RNovelistDto novelistDto, Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null) {
            throw new BadRequestException("Novelsit dont exists");
        }

        if (novelistDto.name() != null) {
            novelist.setName(novelistDto.name());
        }
        novelistRepository.save(novelist);
        return new RDefaultResponse("Novelist updated");
    }
}
