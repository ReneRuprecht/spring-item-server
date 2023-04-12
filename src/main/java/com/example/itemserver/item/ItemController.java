package com.example.itemserver.item;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    @GetMapping(value = "")
    public String getInfo() {
        return "ItemController";
    }
}
