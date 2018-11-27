package com.gearfound.itemservice.web;


import com.gearfound.itemservice.items.found.FoundItemService;
import com.gearfound.itemservice.items.lost.LostItemService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class AbstractControllerTest {

    @Autowired
    protected WebTestClient webClient;

    @MockBean
    LostItemService lostItemService;

    @MockBean
    FoundItemService foundItemService;
}
