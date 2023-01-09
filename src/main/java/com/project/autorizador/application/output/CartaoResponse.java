package com.project.autorizador.application.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CartaoResponse {

    private String senha;
    private String numeroCartao;

}
