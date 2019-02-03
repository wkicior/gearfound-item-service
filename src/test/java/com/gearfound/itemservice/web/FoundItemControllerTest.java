package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;

class FoundItemControllerTest extends AbstractControllerTest {

    @Test
    void getAllFoundItems() {
        //given
        FoundItem foundItem = new FoundItem();
        when(foundItemService.getAllFoundItems()).thenReturn(Flux.just(foundItem));

        //when, then
        webClient.get().uri("/found-items")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FoundItem.class)
                .isEqualTo(Arrays.asList(foundItem));
    }

    @Test
    void searchFoundItems() {
        //given
        FoundItem lostItem = new FoundItem();
        when(foundItemService.searchLostItems("new iphone")).thenReturn(Flux.just(lostItem));

        //when, then
        webClient.get().uri("/found-items?search=new iphone")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FoundItem.class)
                .isEqualTo(Arrays.asList(lostItem));
    }
}