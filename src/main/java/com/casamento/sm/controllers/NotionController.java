package com.casamento.sm.controllers;

import com.casamento.sm.models.ObjectList;
import com.casamento.sm.services.NotionService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

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

    @QueryMapping("getPixAna")
    public String getPixAna(){
        return notionService.getPix_key_ana();
    }

    @QueryMapping
    public Boolean mark(@Argument String id, @Argument String person, @Argument int quantity, @Argument int quantityPrimary) {
        return notionService.mark(id, person, quantity, quantityPrimary);
    }
}
