package br.com.assembleia.controller;

import br.com.assembleia.domain.Voto;
import br.com.assembleia.requests.VotoPostRequestBody;
import br.com.assembleia.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Voto")
@RequestMapping(value = "v1/voto")
@RestController
@AllArgsConstructor
public class VotoController {
    private final VotoService votoService;

    @ApiOperation("List Votos")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Voto>> findAll() {
        return ResponseEntity.of(votoService.findAllVotos());
    }

    @ApiOperation("Find Voto By Id")
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Voto> findById(@RequestParam Long idVoto) {
        return ResponseEntity.of(votoService.findByIdOrThrowBadRequestException(idVoto));
    }

    @ApiOperation("Save Voto")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Voto> save(@RequestBody VotoPostRequestBody votoPostRequestBody) {
        return ResponseEntity.of(votoService.saveVoto(votoPostRequestBody));
    }

    @ApiOperation("Delete Voto By Id")
    @DeleteMapping(value = "/{idVoto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long idVoto) {
        votoService.deleteVoto(idVoto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
