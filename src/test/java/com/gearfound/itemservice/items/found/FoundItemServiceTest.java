package com.gearfound.itemservice.items.found;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoundItemServiceTest {
    private static final String REGISTRANT_ID = "some-user-id";
    private static final String FOUND_ITEM_ID = "some-found-item-id";
    @Mock
    FoundItemRepository foundItemRepository;

    @InjectMocks
    FoundItemService foundItemService;

    @Test
    void save() {
        // given
        FoundItem sampleFoundItem = new FoundItem();
        when(foundItemRepository.save(sampleFoundItem)).thenReturn(Mono.just(sampleFoundItem));

        //when
        Mono<FoundItem> savedFoundItemMono = foundItemService.save(REGISTRANT_ID, sampleFoundItem);

        //then
        FoundItem savedFoundItem = savedFoundItemMono.block();
        assertThat(savedFoundItem).isEqualTo(sampleFoundItem);
        assertThat(savedFoundItem.getRegistrantId()).isEqualTo(REGISTRANT_ID);
    }

    @Test
    void findAll() {
        //given
        FoundItem sampleFoundItem = new FoundItem();
        when(foundItemRepository.findAll()).thenReturn(Flux.just(sampleFoundItem));

        //when
        Flux<FoundItem> savedFoundItems = foundItemService.getAllFoundItems();

        //then
        assertEquals(savedFoundItems.blockFirst(), sampleFoundItem);
    }

    @Test
    void getUserFoundItems() {
        //given
        FoundItem sampleFoundItem = new FoundItem();
        when(foundItemRepository.findAllByRegistrantId(REGISTRANT_ID)).thenReturn(Flux.just(sampleFoundItem));

        //when
        Flux<FoundItem> savedFoundItems = foundItemService.getUserLostItems(REGISTRANT_ID);

        //then
        assertEquals(savedFoundItems.blockFirst(), sampleFoundItem);
    }

    @Test
    void search() {
        //given
        FoundItem sampleLostItem = new FoundItem();
        when(foundItemRepository.searchFoundItems("some phrase")).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<FoundItem> savedLostItems = foundItemService.searchLostItems("some phrase");

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }

    @Test
    void findByIdReturnsFoundItemById() {
        //given
        FoundItem sampleFoundItem = new FoundItem();
        when(foundItemRepository.findById(FOUND_ITEM_ID)).thenReturn(Mono.just(sampleFoundItem));

        //when
        Mono<FoundItem> FoundItem = foundItemService.getFoundItemById(FOUND_ITEM_ID);

        //then
        assertThat(FoundItem.block()).isEqualTo(sampleFoundItem);
    }

    @Test
    void findByIdReturnsFoundItemNotFound() {
        //given
        when(foundItemRepository.findById(FOUND_ITEM_ID)).thenReturn(Mono.empty());

        //when
        Mono<FoundItem> FoundItem = foundItemService.getFoundItemById(FOUND_ITEM_ID);

        //then
        assertThrows(FoundItemNotFoundException.class, FoundItem::block);
    }
}