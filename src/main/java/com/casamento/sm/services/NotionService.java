package com.casamento.sm.services;

import com.casamento.sm.models.MarkList;
import com.casamento.sm.models.ObjectList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NotionService {

    @Value("${database_id}")
    private String database_id;
    @Value("${token}")
    private String token;

    private String url_update = "https://api.notion.com/v1/pages/";
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private WebClient.Builder webClientBuilder;


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Notion-Version", "2022-06-28");
        headers.set("Content-Type", "application/json");

        return headers;
    }

    public ObjectList getList(){
        String url = "https://api.notion.com/v1/databases/" + database_id + "/query";
        HttpHeaders headers = this.getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ObjectList> response = restTemplate.exchange(url, HttpMethod.POST, entity, ObjectList.class);
        return response.getBody();
    }

    public Boolean mark(String id, String person){
        String url =  this.url_update + id;
        WebClient webClient = webClientBuilder.build();
        HttpHeaders headers = this.getHttpHeaders();

        MarkList markList = new MarkList();
        markList.updatePessoa(person);
        String json = convertMarkListToJson(markList);

        return webClient
                .patch()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(json)
                .retrieve()
                .toBodilessEntity()
                .doOnNext(response -> {
                    HttpHeaders responseHeaders = response.getHeaders();
                    responseHeaders.forEach((name, values) -> {
                        values.forEach(value -> System.out.println(name + ": " + value));
                    });
                })
                .doOnError(e -> {
                    System.err.println("Error occurred: " + e.getMessage());
                })
                .map(response -> response.getStatusCode() == HttpStatus.OK)
                .onErrorReturn(false)
                .block();
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String convertMarkListToJson(MarkList markList) {
        try {
            return objectMapper.writeValueAsString(markList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
