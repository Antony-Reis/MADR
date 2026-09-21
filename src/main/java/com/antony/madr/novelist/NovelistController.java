package com.antony.madr.novelist;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/novelist")
@Validated
public class NovelistController {
    private final NovelistService novelistService;

    public NovelistController(NovelistService novelistService) {
        this.novelistService = novelistService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<NovelistEntity> getNovelists(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        return novelistService.ListAllPage(page, size);
    }

    @GetMapping("/byId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NovelistEntity getNovelist(@PathVariable Integer id)throws  BadRequestException{
        return novelistService.GetPerId(id);
    }

    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Page<NovelistEntity> getNovelistsByName(@PathVariable String name,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size){
        return novelistService.ListPerName(name, page, size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void postNovelist(@RequestBody @Valid NovelistDto body) throws BadRequestException {
        novelistService.CreateNovelist(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNovelist(@PathVariable Integer id){
        novelistService.DeleteNovelist(id);
    }
}
