package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.LostItemNotFoundException;
import com.gearfound.itemservice.items.lost.LostItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;

class LostItemControllerTest extends AbstractControllerTest {
    private static final String LOST_ITEM_ID = "some-lost-item-id";

    @Test
    void getAllLostItems() {
        //given
        LostItem lostItem = new LostItem();
        when(lostItemService.getAllLostItems()).thenReturn(Flux.just(lostItem));

        //when, then
        webClient.get().uri("/lost-items")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LostItem.class)
                .isEqualTo(Collections.singletonList(lostItem));
    }

    @Test
    void getLostItemById() {
        //given
        LostItem lostItem = new LostItem();
        when(lostItemService.getLostItemById(LOST_ITEM_ID)).thenReturn(Mono.just(lostItem));

        //when, then
        webClient.get().uri("/lost-items/" + LOST_ITEM_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LostItem.class)
                .isEqualTo(lostItem);
    }

    @Test
    void getLostItemByIdReturns404IfLostItemIsNotFound() {
        //given
        when(lostItemService.getLostItemById(LOST_ITEM_ID)).thenReturn(Mono.defer(() -> Mono.error(new LostItemNotFoundException())));

        //when, then
        webClient.get().uri("/lost-items/" + LOST_ITEM_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void searchLostItems() {
        //given
        LostItem lostItem = new LostItem();
        when(lostItemService.searchLostItems("new iphone")).thenReturn(Flux.just(lostItem));

        //when, then
        webClient.get().uri("/lost-items?search=new iphone")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LostItem.class)
                .isEqualTo(Collections.singletonList(lostItem));
    }
}