package com.desafiojavajr.transacao_api.controller.dtos;
// este é um record que representa a saida das estatisticas
public record EstatisticasResponseDTO(Long count,
                                      Double sum,
                                      Double avg,
                                      Double min,
                                      Double max) {
}