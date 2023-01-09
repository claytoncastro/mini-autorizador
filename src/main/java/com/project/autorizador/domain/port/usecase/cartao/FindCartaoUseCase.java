package com.project.autorizador.domain.port.usecase.cartao;

import com.project.autorizador.domain.entity.Cartao;

public interface FindCartaoUseCase {
    Cartao findCartaoByNumeroCartao(String numeroCartao);
}
