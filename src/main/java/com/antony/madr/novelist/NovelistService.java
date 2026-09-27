package com.antony.madr.novelist;

import com.antony.madr.infra.exceptions.ConflictException;
import com.antony.madr.infra.exceptions.EExceptionsRolesTypes;
import com.antony.madr.infra.exceptions.NotFoundException;
import com.antony.madr.utils.RDefaultResponse;
import com.antony.madr.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NovelistService {
    private final INovelistRepository novelistRepository;

    public NovelistService(INovelistRepository novelistRepository) {
        this.novelistRepository = novelistRepository;
    }

    public RNovelistResponseDto getPerId(Integer id) throws NotFoundException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new NotFoundException(EExceptionsRolesTypes.Novelist);
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

    public RDefaultResponse createNovelist(RNovelistDto RNovelistDto) throws ConflictException {
        String nameNovelistTrimLower = RNovelistDto.name().trim().toLowerCase();
        RNovelistDto = new RNovelistDto(nameNovelistTrimLower);

        NovelistEntity novelist = novelistRepository.findByName(RNovelistDto.name()).orElse(null);

        if (novelist != null) {
            throw new ConflictException(EExceptionsRolesTypes.Novelist);
        }
        novelistRepository.save(new NovelistEntity(RNovelistDto.name()));

        return new RDefaultResponse(HttpStatus.CREATED,"Novelist created successfully");
    }

    public RDefaultResponse deleteNovelist(Integer id) throws NotFoundException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new NotFoundException(EExceptionsRolesTypes.Novelist);
        }
        for (BookEntity book : novelist.getBooks()){
            book.setNovelist(null);
        }
        novelist.getBooks().clear();

        novelistRepository.delete(novelist);
        return new RDefaultResponse(HttpStatus.OK,"Novelist deleted successfully");
    }

    public RDefaultResponse patchNovelist(RNovelistDto novelistDto, Integer id) throws NotFoundException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null) {
            throw new NotFoundException(EExceptionsRolesTypes.Novelist);
        }

        if (novelistDto.name() != null) {
            novelist.setName(novelistDto.name());
        }
        novelistRepository.save(novelist);
        return new RDefaultResponse(HttpStatus.OK,"Novelist updated successfully");
    }
}
