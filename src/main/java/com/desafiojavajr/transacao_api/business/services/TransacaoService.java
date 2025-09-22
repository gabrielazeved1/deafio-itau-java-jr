package com.desafiojavajr.transacao_api.business.services;

import com.desafiojavajr.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.desafiojavajr.transacao_api.infrastructure.exceptions.UnprocessableEntity;
// imports do lombok eses 2 ai eabaixo
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// imports do spring e do java
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {
    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    // metodo para adicionar uma nova transacao na lista
    public void adicionarTransacoes(TransacaoRequestDTO dto) {
        log.info("iniciado o processamento de gravar transacoes " + dto);
        // verifica se a data da transacao é no futuro o que nao é permitido
        if (dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("data e hora maiores que a data atual");
            // lanca uma excecao customizada se a data for invalida
            throw new UnprocessableEntity("data e hora maiores que a data e hora atuais");
        }
        // verifica se o valor da transacao é negativo
        if (dto.valor() < 0) {
            log.error("valor nao pode ser menor que 0");
            // lanca uma excecao customizada se o valor for invalido
            throw new UnprocessableEntity("valor nao pode ser menor que 0");
        }

        // se tudo deu certo joga na lista.....
        listaTransacoes.add(dto);
        log.info("transacoes adicionadas com sucesso");
    }

    // metodo para limpar todas as transacoes da lista
    public void limparTransacoes() {
        log.info("iniciado processamento para deletar transacoes");
        listaTransacoes.clear(); // o metodo clear remove todos os elementos
        log.info("transacoes deletadas com sucesso");
    }

    // metodo para buscar transacoes em um intervalo de tempo em segundos
    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca) {
        log.info("inicadas buscas de transacoes por tempo " + intervaloBusca);
        // calcula o tempo limite para a busca baseado no agora menos o intervalo
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        log.info("retorno de transacoes com sucesso");
        // usa stream para filtrar a lista e retornar apenas as transacoes dentro do intervalo
        return listaTransacoes.stream()
                .filter(transacao -> transacao.dataHora()
                        .isAfter(dataHoraIntervalo)).toList();

    }
}