package com.project.autorizador.domain.adapters.cartao;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
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

@DisplayName("Teste do CreateCartaoUseCaseImpl")
class CreateCartaoUseCaseImplTest {

    private CreateCartaoUseCaseImpl createCartaoUseCase;

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
        createCartaoUseCase = new CreateCartaoUseCaseImpl(cartaoRepository, findCartaoUseCase);
    }

    @Test
    @DisplayName("Método 'save' salva e retorna um cartão quando com sucesso")
    void save_SalvaERetornaUmCartao_QuandoComSucesso(){
        Cartao cartaoToSave = easyRandom.nextObject(Cartao.class);

        when(findCartaoUseCase.findCartaoByNumeroCartao(any())).thenReturn(new Cartao());
        when(cartaoRepository.save(any())).thenReturn(cartaoToSave);

        Cartao cartaoSaved = createCartaoUseCase.save(cartaoToSave);

        assertThat(cartaoSaved)
                .isNotNull()
                .isEqualTo(cartaoToSave);
        assertThat(cartaoSaved.getNumeroCartao()).isEqualTo(cartaoToSave.getNumeroCartao());
        assertThat(cartaoSaved.getSaldo()).isEqualTo(cartaoToSave.getSaldo());
        assertThat(cartaoSaved.getSenha()).isEqualTo(cartaoToSave.getSenha());
        assertThat(cartaoSaved.getId()).isEqualTo(cartaoToSave.getId());

    }

    @Test
    @DisplayName("Método 'save' lança 'ResourceAlreadyExistException' quando cadastrar mesmo número do cartão")
    void save_LancaResourceAlreadyExistException_QuandoComFalha(){
        Cartao cartaoToSave = easyRandom.nextObject(Cartao.class);

        when(findCartaoUseCase.findCartaoByNumeroCartao(any()))
                .thenThrow(new ResourceAlreadyExistException("Cartão já existe!"));

        assertThrows(ResourceAlreadyExistException.class, () -> createCartaoUseCase.save(cartaoToSave));
    }


}