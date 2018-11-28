package com.gearfound.itemservice.items.lost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LostItemServiceTest {
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
        Mono<LostItem> savedLostItem = lostItemService.save(sampleLostItem);

        //then
        assertEquals(savedLostItem.block(), sampleLostItem);
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
    void search() {
        //given
        LostItem sampleLostItem = new LostItem();
        when(lostItemRepository.searchLostItems("some phrase")).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<LostItem> savedLostItems = lostItemService.searchLostItems("some phrase");

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }
}