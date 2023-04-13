package com.example.itemserver.item;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.itemserver.item.exception.ItemNotFoundException;
import com.example.itemserver.item.request.ItemCreateRequest;
import com.example.itemserver.item.request.MultipleItemCreateRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return this.itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return this.itemRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("Item with id: %d does not exist", id)));

    }

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
