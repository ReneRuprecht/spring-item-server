package com.example.itemserver.item.request;

import java.math.BigDecimal;

public record ItemCreateRequest(String name, BigDecimal regularPrice, BigDecimal discountPrice, String description) {
}
