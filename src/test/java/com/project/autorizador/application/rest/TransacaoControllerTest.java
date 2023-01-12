package com.project.autorizador.application.rest;

import com.project.autorizador.application.input.CartaoPostRequest;
import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.exception.ResourceUnprocessableException;
import com.project.autorizador.domain.port.usecase.cartao.UpdateCartaoUseCase;
import com.project.autorizador.util.ControllerTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;

import static com.project.autorizador.application.rest.TransacaoController.TRANSACAO_URL;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransacaoController.class)
@DisplayName("Teste do Controller de Transação")
class TransacaoControllerTest extends ControllerTestBase {

    @MockBean
    private UpdateCartaoUseCase updateCartaoUseCase;

    /**
     * Tests {@link TransacaoController#save(CartaoPostRequest)}
     */
    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("Classe interna para Salvar uma transação")
    class SaveTransacao {
        private static final String URL = TRANSACAO_URL;
        private Cartao cartao = easyRandom.nextObject(Cartao.class);
        private CartaoResponse cartaoResponse = easyRandom.nextObject(CartaoResponse.class);
        private CartaoPostRequest cartaoRequest = easyRandom.nextObject(CartaoPostRequest.class);

        @BeforeEach
        void mapper() {
            when(modelMapper.map(any(CartaoPostRequest.class), any())).thenReturn(cartao);
            when(modelMapper.map(any(Cartao.class), any())).thenReturn(cartaoResponse);
        }

        @AfterEach
        void tearDown() {
            reset(updateCartaoUseCase);
        }

        @Test
        @DisplayName("Deve salvar uma transação com sucesso")
        void withSuccess() throws Exception {
            when(updateCartaoUseCase.update(any(), any())).thenReturn(cartao);
            doPost(URL, cartaoRequest, CREATED);
            verify(updateCartaoUseCase).update(any(), any());
        }

        @Test
        @DisplayName("Deve lançar 'ResourceNotFoundException' quando cartão não existir")
        void cartaoNotFoundException() throws Exception {
            when(updateCartaoUseCase.update(any(), any())).thenThrow(new ResourceNotFoundException("Cartão não existe!"));
            ResultActions resultActions = doPost(URL, cartaoRequest, NOT_FOUND);
            resultActions.andExpect(status().isNotFound());
            verify(updateCartaoUseCase).update(any(), any());
        }

        @Test
        @DisplayName("Deve lançar 'ResourceUnprocessableException' quando saldo for insuficiente")
        void cartaoUnprocessableException() throws Exception {
            when(updateCartaoUseCase.update(any(), any())).thenThrow(new ResourceUnprocessableException("Saldo insuficiente!"));
            ResultActions resultActions = doPost(URL, cartaoRequest, UNPROCESSABLE_ENTITY);
            resultActions.andExpect(status().isUnprocessableEntity());
            verify(updateCartaoUseCase).update(any(), any());
        }

    }

}