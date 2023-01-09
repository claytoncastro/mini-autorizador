package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;

import java.util.Optional;

public class CreateCartaoUseCaseImpl implements CreateCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    private final FindCartaoUseCase findCartaoUseCase;

    public CreateCartaoUseCaseImpl(CartaoRepository cartaoRepository, FindCartaoUseCase findCartaoUseCase) {
        this.cartaoRepository = cartaoRepository;
        this.findCartaoUseCase = findCartaoUseCase;
    }

    @Override
    public Cartao save(Cartao cartao) {
        isCartaoValido(cartao.getNumeroCartao());
        cartao.setSaldo(500.0);
        return cartaoRepository.save(cartao);
    }

    private void isCartaoValido(String numeroCartao) {
        Optional.ofNullable(findCartaoUseCase.findCartaoByNumeroCartao(numeroCartao))
                .filter(obj -> obj.getNumeroCartao() == null)
                .orElseThrow(() -> new ResourceAlreadyExistException("Cartão com número '" + numeroCartao + "' já existe!"));
    }

}
