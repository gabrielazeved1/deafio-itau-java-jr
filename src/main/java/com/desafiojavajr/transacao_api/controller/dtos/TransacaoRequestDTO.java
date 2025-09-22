package com.desafiojavajr.transacao_api.controller.dtos;
import java.time.OffsetDateTime;

// record == representa os dados de entrada de uma transacao
// records sao imutaveis e ja criam getters construtor equals e hashcode automaticamente
public record TransacaoRequestDTO(Double valor, OffsetDateTime dataHora) {
}