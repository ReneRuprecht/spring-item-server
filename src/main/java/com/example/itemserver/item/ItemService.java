package com.example.itemserver.item;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.itemserver.item.request.ItemCreateRequest;
import com.example.itemserver.item.request.MultipleItemCreateRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item addItem(ItemCreateRequest itemCreateRequest) {
        Item item = Item.builder()
            .name(itemCreateRequest.getName())
            .regularPrice(itemCreateRequest.getRegularPrice())
            .discountPrice(itemCreateRequest.getDiscountPrice())
            .description(itemCreateRequest.getDescription())
            .build();

        return this.itemRepository.save(item);
    }

    public List<Item> addMultipleItems(MultipleItemCreateRequest multipleItemCreateRequest) {
        return this.itemRepository.saveAll(multipleItemCreateRequest.items());
    }
}
