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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoundItemServiceTest {
    private static final String REGISTRANT_ID = "some-user-id";
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
    void search() {
        //given
        FoundItem sampleLostItem = new FoundItem();
        when(foundItemRepository.searchFoundItems("some phrase")).thenReturn(Flux.just(sampleLostItem));

        //when
        Flux<FoundItem> savedLostItems = foundItemService.searchLostItems("some phrase");

        //then
        assertEquals(savedLostItems.blockFirst(), sampleLostItem);
    }
}