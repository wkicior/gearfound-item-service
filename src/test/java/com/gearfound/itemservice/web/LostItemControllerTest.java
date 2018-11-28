package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.LostItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.when;

class LostItemControllerTest extends AbstractControllerTest {

       @Test
    void postLostItem() throws Exception {
        //given
        LostItem lostItemInput = new LostItem();
        lostItemInput.setName("some name");
        LostItem lostItemSaved = new LostItem();
        lostItemSaved.setId("1234");
        when(lostItemService.save(lostItemInput)).thenReturn(Mono.just(lostItemSaved));

        //when, then
        webClient.post().uri("/lost-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(lostItemInput))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LostItem.class)
                .isEqualTo(lostItemSaved);
    }

    @Test
    void postLostItemValidate() {
        //given
        LostItem lostItemInput = new LostItem();

        //when, then
        webClient.post().uri("/lost-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(lostItemInput))
                .exchange()
                .expectStatus().isBadRequest();
    }

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
                .isEqualTo(Arrays.asList(lostItem));
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
                .isEqualTo(Arrays.asList(lostItem));
    }
}