package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;

public class CreateCartaoUseCaseImpl implements CreateCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public CreateCartaoUseCaseImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public Cartao save(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

}
