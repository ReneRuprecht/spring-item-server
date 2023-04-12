package com.example.itemserver.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itemserver.item.request.ItemCreateRequest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/item")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String getInfo() {
        return "ItemController";
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody ItemCreateRequest itemCreateRequest) {

        Item item = this.itemService.addItem(itemCreateRequest);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }
}
