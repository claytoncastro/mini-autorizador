package com.project.autorizador.infrastructure.repository.mysql.repository.repository;

import com.project.autorizador.domain.entity.Cartao;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.infrastructure.repository.mysql.repository.entity.CartaoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MysqlDBCartaoRepository implements CartaoRepository {

    private final SpringDataMysqlCartaoRepository cartaoRepository;

    private final ModelMapper modelMapper;

    public MysqlDBCartaoRepository(SpringDataMysqlCartaoRepository cartaoRepository, ModelMapper modelMapper) {
        this.cartaoRepository = cartaoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cartao save(Cartao cartao) {
        CartaoEntity entity = modelMapper.map(cartao, CartaoEntity.class);
        CartaoEntity entitySaved = cartaoRepository.save(entity);
        return modelMapper.map(entitySaved, Cartao.class);
    }

    @Override
    public Cartao findCartaoByNumeroCartao(String numeroCartao) {
        CartaoEntity entityFound = cartaoRepository.findCartaoByNumeroCartao(numeroCartao);
        return modelMapper.map(entityFound == null ? new CartaoEntity() : entityFound, Cartao.class);
    }

    @Override
    public Cartao update(Cartao cartao) {
        return save(cartao);
    }

}
