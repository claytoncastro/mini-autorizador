package com.project.autorizador.infrastructure.configuration;

import com.project.autorizador.application.output.CartaoResponse;
import com.project.autorizador.domain.entity.Cartao;
import lombok.NoArgsConstructor;
import org.modelmapper.PropertyMap;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MappingToModel {

    public static final PropertyMap<Cartao, CartaoResponse> mapCartaoToCartaoResponse = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setNumeroCartao(source.getNumeroCartao());
            map().setSaldo(source.getSaldo());
        }
    };

}
