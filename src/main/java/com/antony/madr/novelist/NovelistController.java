package com.antony.madr.novelist;

import com.antony.madr.utils.RDefaultResponse;
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

    @GetMapping("/byId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RNovelistResponseDto getNovelist(@PathVariable Integer id)throws  BadRequestException{
        return novelistService.getPerId(id);
    }

    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Page<RNovelistResponseDto> getNovelistsByName(@PathVariable String name,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size){
        return novelistService.listPerName(name, page, size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RDefaultResponse postNovelist(@RequestBody @Valid RNovelistDto body) throws BadRequestException {
        return novelistService.createNovelist(body);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse patchNovelist(@PathVariable Integer id, @RequestBody @Valid RNovelistDto body) throws BadRequestException{
        return novelistService.patchNovelist(body, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RDefaultResponse deleteNovelist(@PathVariable Integer id) throws BadRequestException {
        return novelistService.deleteNovelist(id);
    }
}
