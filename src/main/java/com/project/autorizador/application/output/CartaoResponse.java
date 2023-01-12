package com.project.autorizador.application.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartaoResponse {

    private String numeroCartao;
    private Double saldo;

}
