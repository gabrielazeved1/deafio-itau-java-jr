package com.desafiojavajr.transacao_api.controller;
import com.desafiojavajr.transacao_api.business.services.TransacaoService;
import com.desafiojavajr.transacao_api.controller.dtos.TransacaoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao") // define a url base para os endpoints
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    @PostMapping // post
    @Operation(description = "endpoint responsavel por adicionar transacoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "transacao gravada com sucesso"),
            @ApiResponse(responseCode = "422", description = "campos nao atendem os requisitos da transacao"),
            @ApiResponse(responseCode = "400", description = "erro de requisicao"),
            @ApiResponse(responseCode = "500", description = "erro interno do servidor")
    })
    // metodo que recebe os dados da transacao no corpo da requisicao
    public ResponseEntity<Void> adicionarTransacao(@RequestBody TransacaoRequestDTO dto) {
        // chama o servico para adicionar a transacao
        transacaoService.adicionarTransacoes(dto);
        // retorna o status http 201 created que indica sucesso na criacao
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping // delete
    @Operation(description = "endpoint responsavel por deletar transacoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transacao deletadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro de requisicao"),
            @ApiResponse(responseCode = "500", description = "erro interno do servidor")
    })
    public ResponseEntity<Void> deletarTransacoes() {
        // chama o servico para limpar a lista de transacoes
        transacaoService.limparTransacoes();
        // retorna o status http 200 ok que indica sucesso
        return ResponseEntity.ok().build();
    }
}