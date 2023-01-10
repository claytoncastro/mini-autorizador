package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;

import java.util.Optional;

public class FindCartaoUseCaseImpl implements FindCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public FindCartaoUseCaseImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public Cartao findCartaoByNumeroCartao(String numeroCartao) {
        return cartaoRepository.findCartaoByNumeroCartao(numeroCartao);
    }

    @Override
    public Cartao obterSaldo(String numeroCartao) {
        return Optional.ofNullable(cartaoRepository.findCartaoByNumeroCartao(numeroCartao))
                .filter(obj -> obj.getNumeroCartao() != null)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão com número '" + numeroCartao + "' não existe!"));
    }

}
