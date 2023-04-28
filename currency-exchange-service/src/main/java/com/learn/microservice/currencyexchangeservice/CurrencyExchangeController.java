package com.learn.microservice.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@Autowired
	private Environment environment;

	private CurrencyExchangeRepository repository;

	public CurrencyExchangeController(CurrencyExchangeRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		logger.info("retrieveExchangeValue called with with {} to {}",from,to);

		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		
		if(currencyExchange==null) {
			throw new RuntimeException("unable to Find Data for from "+from+" to "+to);
		}

		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);

		return currencyExchange;

	}
}
