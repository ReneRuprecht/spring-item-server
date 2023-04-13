package com.example.itemserver.item;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.example.itemserver.item.exception.ItemNotFoundException;
import com.example.itemserver.item.request.ItemCreateRequest;
import com.example.itemserver.item.request.MultipleItemCreateRequest;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService underTest;

    @Captor
    ArgumentCaptor<List<Item>> listItemCaptor;

    @Captor
    ArgumentCaptor<Item> singleItemCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository);
    }

    @Test
    void shouldAddSingleItem() {
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setName("item name");
        itemCreateRequest.setRegularPrice(new BigDecimal(2.0));
        itemCreateRequest.setDiscountPrice(new BigDecimal(1.0));
        itemCreateRequest.setDescription("item desc");

        underTest.addItem(itemCreateRequest);

        verify(itemRepository).save(singleItemCaptor.capture());

        Item capturedItem = singleItemCaptor.getValue();

        assertThat(capturedItem.getName()).isEqualTo(itemCreateRequest.getName());
    }

    @Test
    void shouldAddMultipleItems() {
        Item item1 = new Item(1L, "item1", new BigDecimal(2.0), new BigDecimal(1.0), "item1 desc");
        Item item2 = new Item(2L, "item2", new BigDecimal(2.0), new BigDecimal(1.0), "item2 desc");
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
        when(itemRepository.findById(1L))
            .thenReturn( Optional.of(new Item(1L, "item l", new BigDecimal(2.0), new BigDecimal(1.0), "item 1 desc")));

        Item testItem = underTest.findItemById(1L);

        verify(itemRepository).findById(1L);

        assertThat(testItem.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowItemNotFoundExceptionWhenItemDoesNotExist() {
        when(itemRepository.findById(1L))
           .thenReturn(Optional.empty());

        assertThatThrownBy(()-> underTest.findItemById(1L)).isInstanceOf(ItemNotFoundException.class).hasMessageContaining("Item with id 1 does not exist");

        verify(itemRepository).findById(1L);
    }
}
