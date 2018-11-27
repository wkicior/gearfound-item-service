package com.gearfound.itemservice.web;

import com.gearfound.itemservice.lost.LostItem;
import com.gearfound.itemservice.lost.LostItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class LostItemControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    LostItemService lostItemService;

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
}