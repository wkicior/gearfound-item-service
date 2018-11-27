package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.LostItem;
import com.gearfound.itemservice.items.lost.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class LostItemController {
    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @PostMapping("/lost-items")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<LostItem> saveLostItem(@RequestBody @Valid LostItem lostItem) {
        return lostItemService.save(lostItem);
    }

    @GetMapping("/lost-items")
    Flux<LostItem> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }
}
