package com.bank.core.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfiguration {

    @Bean
    ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper;
    }

}