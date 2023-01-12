package com.project.autorizador.application.rest;

import com.project.autorizador.application.input.CartaoRequest;
import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.exception.ResourceAlreadyExistException;
import com.project.autorizador.domain.exception.ResourceNotFoundException;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
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

import static com.project.autorizador.application.rest.CartaoController.CARTAO_URL;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartaoController.class)
@DisplayName("Teste do Controller de Cartão")
class CartaoControllerTest extends ControllerTestBase {

    @MockBean
    private CreateCartaoUseCase createCartaoUseCase;

    @MockBean
    private FindCartaoUseCase findCartaoUseCase;


    /**
     * Tests {@link CartaoController#save(CartaoRequest)}
     */
    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("Classe interna para Salvar Cartão")
    class SaveCartao {

        private static final String URL = CARTAO_URL;
        private CartaoRequest cartaoRequest = easyRandom.nextObject(CartaoRequest.class);
        private CartaoResponse cartaoResponse = easyRandom.nextObject(CartaoResponse.class);
        private Cartao cartao = easyRandom.nextObject(Cartao.class);

        @BeforeEach
        void mapper() {
            when(modelMapper.map(any(CartaoRequest.class), any())).thenReturn(cartao);
            when(modelMapper.map(any(Cartao.class), any())).thenReturn(cartaoResponse);
        }

        @AfterEach
        void tearDown() {
            reset(createCartaoUseCase);
        }

        @Test
        @DisplayName("Deve salvar um cartão com sucesso")
        void withSuccess() throws Exception {
            when(createCartaoUseCase.save(any())).thenReturn(cartao);
            doPost(URL, cartaoRequest, CREATED);
            verify(createCartaoUseCase).save(any());
        }

        @Test
        @DisplayName("Deve lançar 'ResourceAlreadyExistException' quando cartão existe")
        void cartaoAlreadyExistException() throws Exception {
            when(createCartaoUseCase.save(any())).thenThrow(new ResourceAlreadyExistException("Cartão já existe!"));
            ResultActions resultActions = doPost(URL, cartaoRequest, UNPROCESSABLE_ENTITY);
            resultActions.andExpect(status().isUnprocessableEntity());
            verify(createCartaoUseCase).save(any());
        }

    }

    /**
     * Tests {@link CartaoController#obterSaldo(String)}
     */
    @Nested
    @TestInstance(PER_CLASS)
    @DisplayName("Classe interna para Obter Saldo do Cartão")
    class ObterSaldo {

        private static final String URL = CARTAO_URL;
        private CartaoResponse cartaoResponse = easyRandom.nextObject(CartaoResponse.class);
        private Cartao cartao = easyRandom.nextObject(Cartao.class);

        @BeforeEach
        void mapper() {
            when(modelMapper.map(any(Cartao.class), any())).thenReturn(cartaoResponse);
        }

        @AfterEach
        void tearDown() {
            reset(findCartaoUseCase);
        }

        @Test
        @DisplayName("Deve retornar um cartão e exibir o saldo com sucesso")
        void withSuccess() throws Exception {
            when(findCartaoUseCase.obterSaldo(any())).thenReturn(cartao);
            doGet((URL + "/8765245311231"), OK);
            verify(findCartaoUseCase).obterSaldo(any());
        }

        @Test
        @DisplayName("Deve lançar 'ResourceNotFoundException' quando cartão não existir")
        void cartaoNotFoundException() throws Exception {
            when(findCartaoUseCase.obterSaldo(any())).thenThrow(new ResourceNotFoundException("Cartão não existe!"));
            ResultActions resultActions = doGet((URL + "/8765245311231"), NOT_FOUND);
            resultActions.andExpect(status().isNotFound());
            verify(findCartaoUseCase).obterSaldo(any());
        }

    }

}