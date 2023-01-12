package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.exception.ResourceUnprocessableException;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Teste do UpdateCartaoUseCaseImpl")
class UpdateCartaoUseCaseImplTest {

    private UpdateCartaoUseCaseImpl updateCartaoUseCase;

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private FindCartaoUseCase findCartaoUseCase;

    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        updateCartaoUseCase = new UpdateCartaoUseCaseImpl(cartaoRepository, findCartaoUseCase);
    }

    @Test
    @DisplayName("Método 'update' atualiza e retorna um cartão quando com sucesso")
    void update_AtualizaERetornaUmCartao_QuandoComSucesso(){
        Cartao cartaoToUpdate = easyRandom.nextObject(Cartao.class);
        cartaoToUpdate.setSaldo(500.00);
        Double valor = 20.5;

        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(cartaoToUpdate);
        when(cartaoRepository.update(any())).thenReturn(cartaoToUpdate);

        Cartao cartaoUpdated = updateCartaoUseCase.update(cartaoToUpdate, valor);

        assertThat(cartaoUpdated)
                .isNotNull()
                .isEqualTo(cartaoToUpdate);
        assertThat(cartaoUpdated.getNumeroCartao()).isEqualTo(cartaoToUpdate.getNumeroCartao());
        assertThat(cartaoUpdated.getSaldo()).isEqualTo(cartaoToUpdate.getSaldo());
        assertThat(cartaoUpdated.getSenha()).isEqualTo(cartaoToUpdate.getSenha());
        assertThat(cartaoUpdated.getId()).isEqualTo(cartaoToUpdate.getId());

    }

    @Test
    @DisplayName("Método 'update' lança 'ResourceNotFoundException' quando número do cartão não existir")
    void update_LancaResourceNotFoundException_QuandoComFalha(){
        Cartao cartaoToUpdate = easyRandom.nextObject(Cartao.class);
        cartaoToUpdate.setSaldo(500.0);
        Double valor = 20.5;
        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(new Cartao());

        assertThrows(ResourceNotFoundException.class, () -> updateCartaoUseCase.update(cartaoToUpdate, valor));
    }

    @Test
    @DisplayName("Método 'update' lança 'ResourceUnprocessableException' quando saldo insuficiente")
    void update_LancaResourceUnprocessableException_QuandoSaldoInsuficiente(){
        Cartao cartaoToUpdate = easyRandom.nextObject(Cartao.class);
        cartaoToUpdate.setSaldo(10.0);
        Double valor = 20.5;
        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(cartaoToUpdate);

        assertThrows(ResourceUnprocessableException.class, () -> updateCartaoUseCase.update(cartaoToUpdate, valor));
    }

    @Test
    @DisplayName("Método 'update' lança 'ResourceUnprocessableException' quando senha é inválida")
    void update_LancaResourceUnprocessableException_QuandoSenhaInvalida(){
        Cartao cartaoToUpdate = easyRandom.nextObject(Cartao.class);
        Cartao cartaoWithOtherPassord = easyRandom.nextObject(Cartao.class);
        cartaoToUpdate.setSaldo(500.0);
        Double valor = 20.5;
        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(cartaoWithOtherPassord);

        assertThrows(ResourceUnprocessableException.class, () -> updateCartaoUseCase.update(cartaoToUpdate, valor));
    }

}