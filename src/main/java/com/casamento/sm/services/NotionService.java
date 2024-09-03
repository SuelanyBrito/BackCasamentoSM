package com.casamento.sm.services;

import com.casamento.sm.models.MarkList;
import com.casamento.sm.models.ObjectList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class NotionService {

    private String url = "https://api.notion.com/v1/databases/35417aedc25f478a8f8e277e8f047dda/query";

    private final RestTemplate restTemplate = new RestTemplate();

    public ObjectList getList(){
        HttpHeaders headers = new HttpHeaders();
        String token = "Bearer secret_WdKX0BpzQ76ShuxAsz8CjIbpQo3MjNYYDBKrgBOGGda";
        headers.set("Authorization", token);
        headers.set("Notion-Version", "2022-06-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ObjectList> response = restTemplate.exchange(url, HttpMethod.POST, entity, ObjectList.class);
        return response.getBody();
    }

    public Boolean mark(String id, String person){
        String url = "https://api.notion.com/v1/pages/" + id;
        HttpHeaders headers = new HttpHeaders();
        String token = "Bearer secret_WdKX0BpzQ76ShuxAsz8CjIbpQo3MjNYYDBKrgBOGGda";
        headers.set("Authorization", token);
        headers.set("Notion-Version", "2022-06-28");
        MarkList markList = new MarkList();
        markList.setObj(true);
        markList.setPerson(person);

        HttpEntity<MarkList> entity = new HttpEntity<>(markList,headers);

        ResponseEntity<ObjectList> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, ObjectList.class);
        return response.getStatusCode() == HttpStatus.OK;
    }
}
