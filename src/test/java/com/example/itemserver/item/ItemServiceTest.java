package com.example.itemserver.item;

import com.example.itemserver.item.exception.ItemNotFoundException;
import com.example.itemserver.item.request.ItemCreateRequest;
import com.example.itemserver.item.request.MultipleItemCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Captor
    ArgumentCaptor<List<Item>> listItemCaptor;
    @Captor
    ArgumentCaptor<Item> singleItemCaptor;
    @Mock
    private ItemRepository itemRepository;
    private ItemService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository);
    }

    @Test
    void shouldAddSingleItem() {
        ItemCreateRequest itemCreateRequest = ItemCreateRequest.builder()
                                                               .marketId(1L)
                                                               .name("item name")
                                                               .regularPrice(new BigDecimal("2.0"))
                                                               .discountPrice(new BigDecimal("1.0"))
                                                               .description("item desc")
                                                               .build();

        underTest.addItem(itemCreateRequest);

        verify(itemRepository).save(singleItemCaptor.capture());

        Item capturedItem = singleItemCaptor.getValue();

        assertThat(capturedItem.getMarketId()).isEqualTo(itemCreateRequest.marketId());
        assertThat(capturedItem.getName()).isEqualTo(itemCreateRequest.name());
        assertThat(capturedItem.getRegularPrice()).isEqualTo(itemCreateRequest.regularPrice());
        assertThat(capturedItem.getDiscountPrice()).isEqualTo(itemCreateRequest.discountPrice());
        assertThat(capturedItem.getDescription()).isEqualTo(itemCreateRequest.description());
    }

    @Test
    void shouldAddMultipleItems() {
        Item item1 = new Item(1L, 2L, "item1", new BigDecimal("2.0"), new BigDecimal("1.0"), "item1 desc");
        Item item2 = new Item(2L, 2L, "item2", new BigDecimal("2.0"), new BigDecimal("1.0"), "item2 desc");
        List<Item> items = List.of(item1, item2);

        MultipleItemCreateRequest multipleItemCreateRequest = new MultipleItemCreateRequest(items);

        underTest.addMultipleItems(multipleItemCreateRequest);

        verify(itemRepository).saveAll(listItemCaptor.capture());

        List<Item> capturedItemList = listItemCaptor.getValue();

        assertThat(capturedItemList).isEqualTo(items);
    }

    @Test
    void canFindAllItems() {
        underTest.findAll();

        verify(itemRepository).findAll();
    }

    @Test
    void shouldFindItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(new Item(1L, 2L, "item l", new BigDecimal("2.0"), new BigDecimal("1.0"), "item 1 desc")));

        Item testItem = underTest.findItemById(1L);

        verify(itemRepository).findById(1L);

        assertThat(testItem.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenItemDoesNotExist() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findItemById(1L)).isInstanceOf(ItemNotFoundException.class)
                                                            .hasMessageContaining("Item with id 1 does not exist");

        verify(itemRepository).findById(1L);
    }
}
