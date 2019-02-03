package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.LostItemNotFoundException;
import com.gearfound.itemservice.items.lost.LostItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.mockito.Mockito.when;

class LostItemControllerTest extends AbstractControllerTest {
    private static final String REGISTRANT_ID = "some-user-id";
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

    @Test
    void postLostItem() throws Exception {
        //given
        LostItem lostItemInput = new LostItem();
        lostItemInput.setName("some name");
        LostItem lostItemSaved = new LostItem();
        lostItemSaved.setId("1234");
        lostItemSaved.setRegistrantId(REGISTRANT_ID);
        when(lostItemService.save(REGISTRANT_ID, lostItemInput)).thenReturn(Mono.just(lostItemSaved));

        //when, then
        webClient.post().uri("/lost-items")
                .header("User-Id", REGISTRANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(lostItemInput))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LostItem.class)
                .isEqualTo(lostItemSaved);
    }

    @Test
    void getUserFoundItems() {
        //given
        LostItem lostItem = new LostItem();
        when(lostItemService.getUserLostItems(REGISTRANT_ID)).thenReturn(Flux.just(lostItem));

        //when, then
        webClient.get().uri("/lost-items?registrantId=" + REGISTRANT_ID)
                .header("User-Id", REGISTRANT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LostItem.class)
                .isEqualTo(Collections.singletonList(lostItem));
    }

    @Test
    void getUserFoundItemsReturnsForbiddenIfUserHasNoAccess() {
        //given
        LostItem lostItem = new LostItem();

        //when, then
        webClient.get().uri("/lost-items?registrantId=som-other-id")
                .header("User-Id", REGISTRANT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden();
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
}