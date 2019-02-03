package com.gearfound.itemservice.items.lost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LostItemServiceTest {
    private static final String REGISTRANT_ID = "some-user-id";
    private static final String LOST_ITEM_ID = "some-lost-item-id";
    @Mock
    LostItemRepository lostItemRepository;

    @InjectMocks
    LostItemService lostItemService;

    @Test
    void save() {
        // given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.save(sampleLostItem)).thenReturn(Mono.just(sampleLostItem));

        //when
        Mono<LostItem> savedLostItemMono = lostItemService.save(REGISTRANT_ID, sampleLostItem);

        //then
        LostItem savedLostItem = savedLostItemMono.block();
        assertThat(savedLostItem).isEqualTo(sampleLostItem);
        assertThat(savedLostItem.getRegistrantId()).isEqualTo(REGISTRANT_ID);
    }

    @Test
    void findAll() {
        //given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.findAll()).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<LostItem> savedLostItems = lostItemService.getAllLostItems();

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }

    @Test
    void findByUserId() {
        //given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.findAllByRegistrantId(REGISTRANT_ID)).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<LostItem> savedLostItems = lostItemService.getUserLostItems(REGISTRANT_ID);

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }

    @Test
    void search() {
        //given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.searchLostItems("some phrase")).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<LostItem> savedLostItems = lostItemService.searchLostItems("some phrase");

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }

    @Test
    void findByIdReturnsFoundItemById() {
        //given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.findById(LOST_ITEM_ID)).thenReturn(Mono.just(sampleLostItem));

        //when
        Mono<LostItem> lostItem = lostItemService.getLostItemById(LOST_ITEM_ID);

        //then
        assertThat(lostItem.block()).isEqualTo(sampleLostItem);
    }

    @Test
    void findByIdReturnsLostItemNotFound() {
        //given
        when(lostItemRepository.findById(LOST_ITEM_ID)).thenReturn(Mono.empty());

        //when
        Mono<LostItem> lostItem = lostItemService.getLostItemById(LOST_ITEM_ID);

        //then
        assertThrows(LostItemNotFoundException.class, lostItem::block);
    }
}