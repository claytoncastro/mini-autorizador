package com.project.autorizador.infrastructure.repository.mysql.repository.repository;

import com.project.autorizador.infrastructure.repository.mysql.repository.entity.CartaoEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Teste do SpringDataMysqlCartaoRepository")
class SpringDataMysqlCartaoRepositoryTest {

    @Autowired
    private SpringDataMysqlCartaoRepository springDataMysqlCartaoRepository;

    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    @DisplayName("Método 'findCartaoByNumeroCartao' retorna um cartão quando com sucesso")
    void findCartaoByNumeroCartao_RetornaUmCartao_QuandoComSucesso() {
        CartaoEntity cartaoToBeSaved = easyRandom.nextObject(CartaoEntity.class);
        CartaoEntity cartaoSaved = springDataMysqlCartaoRepository.save(cartaoToBeSaved);

        CartaoEntity cartaoFound = springDataMysqlCartaoRepository.findCartaoByNumeroCartao(cartaoSaved.getNumeroCartao());

        assertThat(cartaoFound).isNotNull();
        assertThat(cartaoFound.getId()).isNotNull();
        assertThat(cartaoFound.getNumeroCartao()).isEqualTo(cartaoToBeSaved.getNumeroCartao());
        assertThat(cartaoFound.getSenha()).isEqualTo(cartaoToBeSaved.getSenha());
        assertThat(cartaoFound.getSaldo()).isEqualTo(cartaoToBeSaved.getSaldo());
    }

}