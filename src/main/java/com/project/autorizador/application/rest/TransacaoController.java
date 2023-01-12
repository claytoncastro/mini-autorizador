package com.project.autorizador.application.rest;

import com.project.autorizador.application.input.CartaoPostRequest;
import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.UpdateCartaoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "Endpoint de Transações")
@RequestMapping(path = "/transacoes")
public class TransacaoController {

    @Autowired
    private UpdateCartaoUseCase updateCartaoUseCase;

    @Autowired
    private CreateCartaoUseCase createCartaoUseCase;

    @Autowired
    private FindCartaoUseCase findCartaoUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Realizar transação de valor do cartão",
            description = "Método responsável por realizar transação de valor do cartão")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    public ResponseEntity<CartaoResponse> save(@RequestBody @Valid CartaoPostRequest cartaoRequest) {
        Double valor = cartaoRequest.getValor();
        Cartao cartaoToUpdate = modelMapper.map(cartaoRequest, Cartao.class);

        Cartao cartao = updateCartaoUseCase.update(cartaoToUpdate, valor);
        return new ResponseEntity<>(modelMapper.map(cartao, CartaoResponse.class), HttpStatus.CREATED);
    }

}
