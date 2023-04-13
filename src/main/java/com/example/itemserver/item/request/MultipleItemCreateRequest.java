package com.example.itemserver.item.request;

import java.util.List;

import com.example.itemserver.item.Item;

public record MultipleItemCreateRequest(List<Item> items) {

}
