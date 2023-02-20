package com.gm.ecommerce;

import com.gm.ecommerce.model.Customer;
import com.gm.ecommerce.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(
                    new Customer("John Smith", "123 Address Road")));

            log.info("Preloading " + repository.save(
                    new Customer("Millan McGaelan", "500 Street Avenue")));
        };
    }
}
