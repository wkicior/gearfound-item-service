package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.when;

class FoundItemControllerTest extends AbstractControllerTest {

    private static final String REGISTRANT_ID = "some-user-id";

    @Test
    void postFoundItem() throws Exception {
        //given
        FoundItem foundItemInput = new FoundItem();
        foundItemInput.setName("some name");
        FoundItem foundItemSaved = new FoundItem();
        foundItemSaved.setId("1234");
        foundItemSaved.setRegistrantId(REGISTRANT_ID);
        when(foundItemService.save(REGISTRANT_ID, foundItemInput)).thenReturn(Mono.just(foundItemSaved));

        //when, then
        webClient.post().uri("/found-items")
                .header("User-Id", REGISTRANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(foundItemInput))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FoundItem.class)
                .isEqualTo(foundItemSaved);
    }

    @Test
    void postFoundItemValidate() {
        //given
        FoundItem foundItemInput = new FoundItem();

        //when, then
        webClient.post().uri("/found-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(foundItemInput))
                .exchange()
                .expectStatus().isBadRequest();
    }

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