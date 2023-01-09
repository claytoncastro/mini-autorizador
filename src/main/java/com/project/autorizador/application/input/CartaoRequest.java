package com.project.autorizador.application.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class CartaoRequest {

    @NotBlank
    private String senha;
    @NotBlank
    private String numeroCartao;

}
