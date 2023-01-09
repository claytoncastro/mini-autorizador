package com.project.autorizador.infrastructure.repository.mysql.repository.repository;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import org.springframework.stereotype.Component;

@Component
public class MysqlDBCartaoRepository implements CartaoRepository {

    @Override
    public Cartao save(Cartao cartao) {
        /*TODO:
           - Converter de Cartao para CartaoEntity
           - Salvar CartaoEntity
           - Converter o CartaoEntity salvo para Cartao
           - Retornar para camada de UseCase
         */
        System.out.println("Test");
        return null;
    }

}
