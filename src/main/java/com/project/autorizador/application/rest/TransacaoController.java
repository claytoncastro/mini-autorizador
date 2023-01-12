package com.project.autorizador.application.rest;

import com.project.autorizador.application.input.CartaoPostRequest;
import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.usecase.cartao.UpdateCartaoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.project.autorizador.application.rest.TransacaoController.TRANSACAO_URL;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Tag(name = "Endpoint de Transações")
@RequestMapping(path = TRANSACAO_URL)
public class TransacaoController {

    public static final String TRANSACAO_URL = "/transacoes";

    @Autowired
    private UpdateCartaoUseCase updateCartaoUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Realizar transação de valor do cartão",
            description = "Método responsável por realizar transação de valor do cartão")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    public ResponseEntity<CartaoResponse> save(@RequestBody @Valid CartaoPostRequest cartaoRequest) {
        Double valor = cartaoRequest.getValor();
        Cartao cartaoToUpdate = modelMapper.map(cartaoRequest, Cartao.class);

        Cartao cartao = updateCartaoUseCase.update(cartaoToUpdate, valor);
        return new ResponseEntity<>(modelMapper.map(cartao, CartaoResponse.class), CREATED);
    }

}
