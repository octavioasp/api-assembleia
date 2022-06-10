package br.com.assembleia.controller;

import br.com.assembleia.domain.Sessao;
import br.com.assembleia.requests.SessaoPostRequestBody;
import br.com.assembleia.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Sessão")
@RequestMapping(value = "v1/sessao")
@RestController
@AllArgsConstructor
public class SessaoController {
    private final SessaoService sessaoService;

    @ApiOperation("List Sessoes")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Sessao>> findAll() {
        return ResponseEntity.of(sessaoService.findAllSessoes());
    }

    @ApiOperation("Find Sessao By Id")
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Sessao> findById(@RequestParam Long idSessao) {
        return ResponseEntity.of(sessaoService.findByIdOrThrowBadRequestException(idSessao));
    }

    @ApiOperation("Save Sessao")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Sessao> save(@RequestBody @Valid SessaoPostRequestBody sessaoPostRequestBody) {
        return ResponseEntity.of(sessaoService.saveSessao(sessaoPostRequestBody));
    }

    @ApiOperation("Delete Sessao By Id")
    @DeleteMapping(value = "/{idSessao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long idSessao) {
        sessaoService.deleteSessao(idSessao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
