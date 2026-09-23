package com.antony.madr.novelist;

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

    public Page<NovelistEntity> listAllPage(Integer page, Integer size){
        Pageable pageable;
        if (size < 20){
                pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        }
        return novelistRepository.findAll(pageable);
    }

    public NovelistEntity getPerId(Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new BadRequestException("Novelsit dont exists");
        }
        return novelist;
    }

    public Page<NovelistEntity> listPerName(String name,Integer page, Integer size){
        Pageable pageable;
        if (size < 20){
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        }

        return novelistRepository.findByNameStartingWith(name, pageable);
    }

    public void createNovelist(RNovelistDto RNovelistDto) throws BadRequestException {
        String nameNovelistTrimLower = RNovelistDto.name().trim().toLowerCase();
        RNovelistDto = new RNovelistDto(nameNovelistTrimLower);

        NovelistEntity novelist = novelistRepository.findByName(RNovelistDto.name()).orElse(null);

        if (novelist != null) {
            throw new BadRequestException("Novelist already exists!");
        }
        novelistRepository.save(new NovelistEntity(RNovelistDto.name()));
    }

    public void deleteNovelist(Integer id){
        novelistRepository.deleteById(id);
    }

    public void patchNovelist(RNovelistDto RNovelistDto, Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null) {
            throw new BadRequestException("Novelsit dont exists");
        }

        if (RNovelistDto.name() != null) {
            novelist.setName(RNovelistDto.name());
        }
        novelistRepository.save(novelist);
    }
}
