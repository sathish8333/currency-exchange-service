package com.sr.microservices.controller;

import com.sr.microservices.entity.CurrencyExchange;
import com.sr.microservices.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeRepository jpaRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchange(
            @PathVariable String from,
            @PathVariable String to
    ) {
        //CurrencyExchange currencyExchange = new CurrencyExchange(10001L, from, to, BigDecimal.valueOf(80));
        CurrencyExchange currencyExchange = jpaRepository.findByFromAndTo(from, to);
        if (currencyExchange == null){
            throw new RuntimeException("unable to get the convertion rate from "+from +" to "+to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
