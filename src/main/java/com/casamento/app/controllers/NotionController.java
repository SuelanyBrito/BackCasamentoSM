package com.casamento.app.controllers;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.casamento.app.models.ObjectList;
import com.casamento.app.services.NotionService;

@Controller
@AllArgsConstructor
public class NotionController {

    private final NotionService notionService;

    @QueryMapping("getList")
    public ObjectList getList(){
        return notionService.getList();
    }

    @QueryMapping("getPix")
    public String getPix(){
        return notionService.getPix_key();
    }

    @QueryMapping
    public Boolean mark(@Argument String id, @Argument String person, @Argument int quantity, @Argument int quantityPrimary) {
        return notionService.mark(id, person, quantity, quantityPrimary);
    }
}
