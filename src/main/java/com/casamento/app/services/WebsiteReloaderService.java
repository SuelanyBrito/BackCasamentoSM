package com.casamento.app.services;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
public class WebsiteReloaderService {
    private static final Logger logger = LoggerFactory.getLogger(WebsiteReloaderService.class);
    private final RestTemplate restTemplate;

    public WebsiteReloaderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 40000)
    public void reloadWebsite() {
        try {
            String url = "https://backcasamentosm.onrender.com/graphql";
            var response = restTemplate.getForEntity(url, String.class);
            logger.info("Reloaded at {}: Status Code {}", LocalDateTime.now(), response.getStatusCode());
        } catch (Exception e) {
            logger.error("Error reloading at {}: {}", LocalDateTime.now(), e.getMessage());
        }
    }
}
