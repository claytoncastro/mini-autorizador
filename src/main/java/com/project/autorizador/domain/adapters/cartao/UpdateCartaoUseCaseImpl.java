package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.exception.ResourceUnprocessableException;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.UpdateCartaoUseCase;

import java.util.Optional;

public class UpdateCartaoUseCaseImpl implements UpdateCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    private final FindCartaoUseCase findCartaoUseCase;

    public UpdateCartaoUseCaseImpl(CartaoRepository cartaoRepository, FindCartaoUseCase findCartaoUseCase) {
        this.cartaoRepository = cartaoRepository;
        this.findCartaoUseCase = findCartaoUseCase;
    }

    @Override
    public Cartao update(Cartao cartaoToUpdate, Double valor) {
        Cartao cartao = obterCartaoByNumeroCartao(cartaoToUpdate.getNumeroCartao());
        isSenhaValida(cartao, cartaoToUpdate.getSenha());
        isSaldoSuficiente(valor, cartao);
        cartao.setSaldo(cartao.getSaldo() - valor);
        return cartaoRepository.update(cartao);
    }

    private Cartao obterCartaoByNumeroCartao(String numeroCartao) {
        return Optional.ofNullable(findCartaoUseCase.findCartaoByNumeroCartao(numeroCartao))
                .filter(obj -> obj.getNumeroCartao() != null)
                .orElseThrow(() -> new ResourceNotFoundException("Cartão com número '" + numeroCartao + "' não existe!"));
    }

    private void isSaldoSuficiente(Double valor, Cartao cartao) {
        Optional.ofNullable(cartao)
                .filter(obj -> (obj.getSaldo() - valor) >= 0)
                .orElseThrow(
                        () -> new ResourceUnprocessableException("Saldo insuficiente. Saldo atual '"
                                + cartao.getSaldo() + "', valor solicitado '" + valor + "'"));
    }

    private void isSenhaValida(Cartao cartao, String senha) {
        Optional.ofNullable(cartao)
                .filter(obj -> senha.equals(obj.getSenha()))
                .orElseThrow(() -> new ResourceUnprocessableException("Senha inválida!"));
    }

}
