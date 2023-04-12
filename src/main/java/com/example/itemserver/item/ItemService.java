package com.example.itemserver.item;

import org.springframework.stereotype.Service;

import com.example.itemserver.item.request.ItemCreateRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item addItem(ItemCreateRequest itemCreateRequest) {
        Item item = Item.builder()
            .name(itemCreateRequest.name())
            .regularPrice(itemCreateRequest.regularPrice())
            .discountPrice(itemCreateRequest.discountPrice())
            .description(itemCreateRequest.description())
            .build();

        return this.itemRepository.save(item);

    }
}
