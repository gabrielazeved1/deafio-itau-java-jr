package com.desafiojavajr.transacao_api.controller;
import com.desafiojavajr.transacao_api.business.services.EstatisticasService;
import com.desafiojavajr.transacao_api.controller.dtos.EstatisticasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica") // define a url base
@RequiredArgsConstructor
public class EstatisticasController {

    // injeta o servico de estatisticas
    private final EstatisticasService estatisticasService;

    @GetMapping //get
    @Operation(description = "endpoint responsavel por buscar estatisticas de transacoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na busca de estatisticas de transacoes"),
            @ApiResponse(responseCode = "500", description = "erro interno do servidor")
    })
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            // define um parametro opcional para o intervalo de tempo com valor padrao de 60 segundos
            @RequestParam(value = "intervaloBusca", required = false, defaultValue = "3000") Integer intervaloBusca) {
        // chama o servico para calcular e retorna o resultado com status http 200 ok
        return ResponseEntity.ok(
                estatisticasService.calcularEstatisticasTransacoes(intervaloBusca));
    }
}