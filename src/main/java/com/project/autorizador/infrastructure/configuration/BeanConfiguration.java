package com.project.autorizador.infrastructure.configuration;

import com.project.autorizador.domain.adapters.cartao.CreateCartaoUseCaseImpl;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);

        return modelMapper;
    }

    @Bean
    public CreateCartaoUseCase createCartaoUseCase(CartaoRepository cartaoRepository) {
        return new CreateCartaoUseCaseImpl(cartaoRepository);
    }

}
