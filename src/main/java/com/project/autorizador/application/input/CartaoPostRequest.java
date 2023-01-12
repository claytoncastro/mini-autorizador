package com.project.autorizador.application.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CartaoPostRequest {

    @NotBlank(message = "Campo 'senha' não pode ser branco ou nulo")
    @Schema(description = "Campo referente a senha do cartão",
            example = "123")
    private String senha;

    @NotBlank(message = "Campo 'numeroCartao' não pode ser branco ou nulo")
    @Schema(description = "Campo referente ao número do cartão",
            example = "87652453112355")
    private String numeroCartao;

    @NotNull(message = "Campo 'valor' não pode ser nulo")
    @Schema(description = "Campo referente ao valor que deverá ser consumido do saldo do cartão",
            example = "10.50")
    private Double valor;

}
