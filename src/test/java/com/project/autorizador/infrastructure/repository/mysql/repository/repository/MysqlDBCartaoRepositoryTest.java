package com.project.autorizador.infrastructure.repository.mysql.repository.repository;

import com.project.autorizador.domain.entity.Cartao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Teste do Repository de Cartão")
class MysqlDBCartaoRepositoryTest {

    @Autowired
    private MysqlDBCartaoRepository cartaoRepository;

    @Mock
    private SpringDataMysqlCartaoRepository springDataMysqlCartaoRepository;

    @Mock
    private ModelMapper modelMapper;

    private static EasyRandom easyRandom;

    @BeforeAll
    public static void beforeTests() {
        easyRandom = new EasyRandom();
    }

    @Test
    @DisplayName("Método 'save' persiste ou atualiza um cartão quando com sucesso")
    void save_PersisteOuAtualizaCartao_QuandoComSucesso() {
        Cartao cartaoToBeSaved = easyRandom.nextObject(Cartao.class);
        cartaoToBeSaved.setId(null);
        Cartao cartaoSaved = cartaoRepository.save(cartaoToBeSaved);

        assertThat(cartaoSaved).isNotNull();
        assertThat(cartaoSaved.getId()).isNotNull();
        assertThat(cartaoSaved.getNumeroCartao()).isEqualTo(cartaoToBeSaved.getNumeroCartao());
        assertThat(cartaoSaved.getSenha()).isEqualTo(cartaoToBeSaved.getSenha());
        assertThat(cartaoSaved.getSaldo()).isEqualTo(cartaoToBeSaved.getSaldo());
    }

    @Test
    @DisplayName("Método 'findCartaoByNumeroCartao' retorna um cartão quando com sucesso")
    void findCartaoByNumeroCartao_RetornaUmCartao_QuandoComSucesso() {
        Cartao cartaoToBeSaved = easyRandom.nextObject(Cartao.class);
        Cartao cartaoSaved = cartaoRepository.save(cartaoToBeSaved);

        Cartao cartaoFound = cartaoRepository.findCartaoByNumeroCartao(cartaoSaved.getNumeroCartao());

        assertThat(cartaoFound).isNotNull();
        assertThat(cartaoFound.getId()).isNotNull();
        assertThat(cartaoFound.getNumeroCartao()).isEqualTo(cartaoToBeSaved.getNumeroCartao());
        assertThat(cartaoFound.getSenha()).isEqualTo(cartaoToBeSaved.getSenha());
        assertThat(cartaoFound.getSaldo()).isEqualTo(cartaoToBeSaved.getSaldo());
    }

    @Test
    @DisplayName("Método 'update' atualiza um cartão quando com sucesso")
    void update_RetornaUmCartao_QuandoComSucesso() {
        Cartao cartaoToBeUpdated = easyRandom.nextObject(Cartao.class);
        Cartao cartaoUpdated = cartaoRepository.update(cartaoToBeUpdated);

        assertThat(cartaoUpdated).isNotNull();
        assertThat(cartaoUpdated.getId()).isNotNull();
        assertThat(cartaoUpdated.getNumeroCartao()).isEqualTo(cartaoToBeUpdated.getNumeroCartao());
        assertThat(cartaoUpdated.getSenha()).isEqualTo(cartaoToBeUpdated.getSenha());
        assertThat(cartaoUpdated.getSaldo()).isEqualTo(cartaoToBeUpdated.getSaldo());
    }

}