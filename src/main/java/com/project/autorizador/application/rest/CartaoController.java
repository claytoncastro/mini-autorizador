package com.project.autorizador.application.rest;

import com.project.autorizador.application.input.CartaoRequest;
import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import static com.project.autorizador.application.rest.CartaoController.CARTAO_URL;

@RestController
@Tag(name = "Endpoint de Cartões")
@RequestMapping(path = CARTAO_URL)
public class CartaoController {

    public static final String CARTAO_URL = "/cartoes";

    @Autowired
    private CreateCartaoUseCase createCartaoUseCase;

    @Autowired
    private FindCartaoUseCase findCartaoUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar novo cartão", description = "Método responsável por criar novo cartão")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created")})
    public ResponseEntity<CartaoResponse> save(@RequestBody @Valid CartaoRequest cartaoRequest) {
        Cartao cartaoToSave = modelMapper.map(cartaoRequest, Cartao.class);
        Cartao cartao = createCartaoUseCase.save(cartaoToSave);
        return new ResponseEntity<>(modelMapper.map(cartao, CartaoResponse.class), CREATED);
    }

    @GetMapping("/{numeroCartao}")
    @ResponseStatus(OK)
    @Operation(summary = "Obter saldo do Cartão", description = "Método responsável por obter saldo do cartão")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<CartaoResponse> obterSaldo(@PathVariable String numeroCartao) {
        Cartao cartao = findCartaoUseCase.obterSaldo(numeroCartao);
        return new ResponseEntity<>(modelMapper.map(cartao, CartaoResponse.class), OK);
    }

}
