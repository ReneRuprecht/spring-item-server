package com.example.itemserver.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itemserver.item.request.ItemCreateRequest;
import com.example.itemserver.item.request.MultipleItemCreateRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = this.itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.findItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest) {

        Item item = this.itemService.addItem(itemCreateRequest);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Item>> addMultipleItems(
            @RequestBody MultipleItemCreateRequest multipleItemCreateRequest) {

        List<Item> items = this.itemService.addMultipleItems(multipleItemCreateRequest);

        return new ResponseEntity<List<Item>>(items, HttpStatus.CREATED);
    }
}
