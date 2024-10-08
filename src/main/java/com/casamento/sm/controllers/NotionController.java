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

    @QueryMapping
    public Boolean mark(@Argument String id, @Argument String person, @Argument int quantity) {
        return notionService.mark(id, person, quantity);
    }
}
