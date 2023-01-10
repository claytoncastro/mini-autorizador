package com.project.autorizador.application.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor
public class CartaoPostRequest {

    @NotBlank
    private String senha;
    @NotBlank
    private String numeroCartao;
    @NotNull
    private Double valor;

}
