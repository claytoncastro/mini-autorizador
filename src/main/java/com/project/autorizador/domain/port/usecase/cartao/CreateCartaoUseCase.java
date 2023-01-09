package com.project.autorizador.domain.port.usecase.cartao;

import com.project.autorizador.domain.entity.Cartao;

public interface CreateCartaoUseCase {
    Cartao save(Cartao cartao);
}
