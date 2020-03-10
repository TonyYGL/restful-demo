package com.example.demo.preload;

import com.example.demo.entity.Order;
import com.example.demo.entity.Status;
import com.example.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabaseOrder(OrderRepository orderRepository) {
        return args -> {
            log.info("Preload : " + orderRepository.save(new Order("Mac Pro", Status.COMPLETED)));
            log.info("Preload : " + orderRepository.save(new Order("Mac Air", Status.IN_PROGRESS)));
        };
    }
}
