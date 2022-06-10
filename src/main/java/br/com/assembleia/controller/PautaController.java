package br.com.assembleia.controller;

import br.com.assembleia.domain.Pauta;
import br.com.assembleia.requests.PautaPostRequestBody;
import br.com.assembleia.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pauta")
@RequestMapping(value = "v1/pauta")
@RestController
@AllArgsConstructor
public class PautaController {
    private final PautaService pautaService;

    @ApiOperation("List Pautas")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Pauta>> findAll() {
        return ResponseEntity.of(pautaService.findAllPautas());
    }

    @ApiOperation("Find Pauta By Id")
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pauta> findById(@RequestParam Long idPauta) {
        return ResponseEntity.of(pautaService.findByIdOrThrowBadRequestException(idPauta));
    }

    @ApiOperation("Save Pauta")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pauta> save(@RequestBody @Valid PautaPostRequestBody pautaPostRequestBody) {
        return ResponseEntity.of(pautaService.savePauta(pautaPostRequestBody));
    }

    @ApiOperation("Delete Pauta By Id")
    @DeleteMapping(value = "/{idPauta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long idPauta) {
        pautaService.deletePauta(idPauta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
