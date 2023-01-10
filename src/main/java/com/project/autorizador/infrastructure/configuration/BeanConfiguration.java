package com.project.autorizador.infrastructure.configuration;

import com.project.autorizador.domain.adapters.cartao.CreateCartaoUseCaseImpl;
import com.project.autorizador.domain.adapters.cartao.FindCartaoUseCaseImpl;
import com.project.autorizador.domain.adapters.cartao.UpdateCartaoUseCaseImpl;
import com.project.autorizador.domain.port.repository.CartaoRepository;
import com.project.autorizador.domain.port.usecase.cartao.CreateCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.FindCartaoUseCase;
import com.project.autorizador.domain.port.usecase.cartao.UpdateCartaoUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.project.autorizador.infrastructure.configuration.MappingToModel.mapCartaoToCartaoResponse;
import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.addMappings(mapCartaoToCartaoResponse);
        return modelMapper;
    }

    @Bean
    public CreateCartaoUseCase createCartaoUseCase(CartaoRepository cartaoRepository, FindCartaoUseCase findCartaoUseCase) {
        return new CreateCartaoUseCaseImpl(cartaoRepository, findCartaoUseCase);
    }

    @Bean
    public FindCartaoUseCase findCartaoUseCase(CartaoRepository cartaoRepository) {
        return new FindCartaoUseCaseImpl(cartaoRepository);
    }

    @Bean
    public UpdateCartaoUseCase updateCartaoUseCase(CartaoRepository cartaoRepository, FindCartaoUseCase findCartaoUseCase) {
        return new UpdateCartaoUseCaseImpl(cartaoRepository, findCartaoUseCase);
    }
}
