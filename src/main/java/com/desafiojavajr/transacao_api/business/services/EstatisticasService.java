package com.desafiojavajr.transacao_api.business.services;

import com.desafiojavajr.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.desafiojavajr.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    // injeta o servico de transacao para poder usar seus metodos
    public final TransacaoService transacaoService;

    // metodo principal que calcula as estatisticas
    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca) {

        log.info("iniciada busca de estatisticas de transacoes pelo periodo de tempo " + intervaloBusca);
        // busca as transacoes recentes usando o transacaoservice
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);
        // se a lista de transacoes estiver vazia retorna um dto com valores zerados
        if (transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        // usa a classe DoubleSummaryStatistics para calcular tudo de uma vez de forma eficiente
        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
                .mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();
        log.info("estatisticas retornadas com sucesso");
        // cria e retorna o dto com os resultados do calculo
        return new EstatisticasResponseDTO(
                estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax()
        );
    }
}