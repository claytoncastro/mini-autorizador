package com.project.autorizador.infrastructure.repository.mysql.repository.repository;

import com.project.autorizador.infrastructure.repository.mysql.repository.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataMysqlCartaoRepository extends JpaRepository<CartaoEntity, Long> {

    @Query("from CartaoEntity c where c.numeroCartao = :numeroCartao")
    CartaoEntity findCartaoByNumeroCartao(String numeroCartao);
}
