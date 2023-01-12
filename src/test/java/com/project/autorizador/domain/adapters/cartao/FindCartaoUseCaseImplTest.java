package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.port.repository.CartaoRepository;
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

@DisplayName("Teste do FindCartaoUseCaseImpl")
class FindCartaoUseCaseImplTest {

    private FindCartaoUseCaseImpl findCartaoUseCase;

    @Mock
    private CartaoRepository cartaoRepository;


    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        findCartaoUseCase = new FindCartaoUseCaseImpl(cartaoRepository);
    }

    @Test
    @DisplayName("Método 'findCartaoByNumeroCartao' retorna um cartão quando com sucesso")
    void findCartaoByNumeroCartao_RetornaUmCartao_QuandoComSucesso(){
        Cartao cartao = easyRandom.nextObject(Cartao.class);
        String numeroCartao = "87652453112355";

        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(cartao);

        Cartao cartaoFound = findCartaoUseCase.findCartaoByNumeroCartao(numeroCartao);

        assertThat(cartaoFound)
                .isNotNull()
                .isEqualTo(cartao);
        assertThat(cartaoFound.getNumeroCartao()).isEqualTo(cartao.getNumeroCartao());
        assertThat(cartaoFound.getSaldo()).isEqualTo(cartao.getSaldo());
        assertThat(cartaoFound.getSenha()).isEqualTo(cartao.getSenha());
        assertThat(cartaoFound.getId()).isEqualTo(cartao.getId());

    }

    @Test
    @DisplayName("Método 'obterSaldo' obtem cartão com saldo quando com sucesso")
    void obterSaldo_ObtemCartaoComSaldo_QuandoComSucesso(){
        Cartao cartao = easyRandom.nextObject(Cartao.class);
        String numeroCartao = "87652453112355";

        when(cartaoRepository.findCartaoByNumeroCartao(any())).thenReturn(cartao);

        Cartao cartaoFound = findCartaoUseCase.obterSaldo(numeroCartao);

        assertThat(cartaoFound)
                .isNotNull()
                .isEqualTo(cartao);
        assertThat(cartaoFound.getNumeroCartao()).isEqualTo(cartao.getNumeroCartao());
        assertThat(cartaoFound.getSaldo()).isEqualTo(cartao.getSaldo());
        assertThat(cartaoFound.getSenha()).isEqualTo(cartao.getSenha());
        assertThat(cartaoFound.getId()).isEqualTo(cartao.getId());
    }

    @Test
    @DisplayName("Método 'obterSaldo' lança 'ResourceNotFoundException' quando número do cartão não existir")
    void obterSaldo_LancaResourceNotFoundException_QuandoComFalha(){
        String numeroCartao = "87652453112355";
        when(cartaoRepository.findCartaoByNumeroCartao(any())).thenReturn(new Cartao());

        assertThrows(ResourceNotFoundException.class, () -> findCartaoUseCase.obterSaldo(numeroCartao));
    }

}