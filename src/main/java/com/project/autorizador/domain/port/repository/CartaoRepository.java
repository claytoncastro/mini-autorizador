package com.project.autorizador.domain.port.repository;

import com.project.autorizador.domain.entity.Cartao;

public interface CartaoRepository {

    Cartao save(Cartao cartao);

    Cartao findCartaoByNumeroCartao(String numeroCartao);

    Cartao update(Cartao cartao);
}
