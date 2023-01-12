package com.project.autorizador.application.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CartaoRequest {

    @NotBlank(message = "Campo 'senha' não pode ser branco ou nulo")
    @Schema(description = "Campo referente a senha do cartão",
            example = "123")
    private String senha;

    @NotBlank(message = "Campo 'numeroCartao' não pode ser branco ou nulo")
    @Schema(description = "Campo referente ao número do cartão",
            example = "87652453112355")
    private String numeroCartao;

}
