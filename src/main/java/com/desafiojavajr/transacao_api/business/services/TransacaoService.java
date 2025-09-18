package com.desafiojavajr.transacao_api.business.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.desafiojavajr.transacao_api.infrastructure.exceptions.UnprocessableEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    //armazenamento em memoria definido
    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionaTransacoes(TransacoesRequestDTO dto){
        log.info( "Inicar o processo de gravar transacoes");

        if(dto.dataHora().isAfter(OffsetDateTime.now())){
            log.error("Data e hora maiores que a data atual");
            throw new UnprocessableEntity("Data e hora maiores que a data atual");
        }
        if(dto.valor()<0){
            throw new UnprocessableEntity("O valor nao pode ser menor que 0");

        }
        listaTransacoes.add(dto);
    }

    public void limparTransacoes(){
        listaTransacoes.clear();
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBusca){
        // data e hora atual menos os segundos do intervalo de busca
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);

        return listaTransacoes.stream()
        .filter(transacoes -> transacoes.dataHora()
            .isAfter(dataHoraIntervalo)).toLust();
//falta o controller
    }
}
