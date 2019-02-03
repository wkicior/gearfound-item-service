package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import com.gearfound.itemservice.items.found.FoundItemNotFoundException;
import com.gearfound.itemservice.items.lost.LostItem;
import com.gearfound.itemservice.items.lost.LostItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;

class FoundItemControllerTest extends AbstractControllerTest {

    private static final String REGISTRANT_ID = "some-user-id";
    private static final String FOUND_ITEM_ID = "some-found-item";

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
                .isEqualTo(Collections.singletonList(foundItem));
    }

    @Test
    void getFoundItemById() {
        //given
        FoundItem foundItem = new FoundItem();
        when(foundItemService.getFoundItemById(FOUND_ITEM_ID)).thenReturn(Mono.just(foundItem));

        //when, then
        webClient.get().uri("/found-items/" + FOUND_ITEM_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FoundItem.class)
                .isEqualTo(foundItem);
    }

    @Test
    void getFoundItemByIdReturns404IfLostItemIsNotFound() {
        //given
        when(foundItemService.getFoundItemById(FOUND_ITEM_ID)).thenReturn(Mono.defer(() -> Mono.error(new FoundItemNotFoundException())));

        //when, then
        webClient.get().uri("/found-items/" + FOUND_ITEM_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
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
                .isEqualTo(Collections.singletonList(lostItem));
    }

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
    void getUserFoundItems() throws Exception {
        //given
        FoundItem foundItem = new FoundItem();
        when(foundItemService.getUserLostItems(REGISTRANT_ID)).thenReturn(Flux.just(foundItem));

        //when, then
        webClient.get().uri("/found-items?registrantId=" + REGISTRANT_ID)
                .header("User-Id", REGISTRANT_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FoundItem.class)
                .isEqualTo(Collections.singletonList(foundItem));
    }

    @Test
    void getUserFoundItemsThrowsForbiddenIfUserAndRegistrantIdDoNotMatch() throws Exception {
        //when, then
        webClient.get().uri("/found-items?registrantId=some-other-registrant-id")
                .header("User-Id", REGISTRANT_ID)
                .exchange()
                .expectStatus().isForbidden();
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
}