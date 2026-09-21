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

    public Page<NovelistEntity> ListAllPage(Integer page, Integer size){
        Pageable pageable;
        if (size < 20){
                pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        }
        return novelistRepository.findAll(pageable);
    }

    public NovelistEntity GetPerId(Integer id) throws BadRequestException{
        NovelistEntity novelist = novelistRepository.findById(id).orElse(null);
        if (novelist == null){
            throw new BadRequestException("Novelsit dont exists");
        }
        return novelist;
    }

    public Page<NovelistEntity> ListPerName(String name,Integer page, Integer size){
        Pageable pageable;
        if (size < 20){
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        }

        return novelistRepository.findByNameStartingWith(name, pageable);
    }

    public void CreateNovelist(NovelistDto novelistDto) throws BadRequestException {
        String novelistTrimLower = novelistDto.getName().trim().toLowerCase();
        novelistDto.setName(novelistTrimLower);

        NovelistEntity novelist = novelistRepository.findByName(novelistDto.getName()).orElse(null);

        if (novelist != null) {
            throw new BadRequestException("Novelist already exists!");
        }
        novelistRepository.save(new NovelistEntity(novelistDto.getName()));
    }

    public void DeleteNovelist(Integer id){
        novelistRepository.deleteById(id);
    }
}
