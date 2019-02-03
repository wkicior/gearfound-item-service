package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.LostItem;
import com.gearfound.itemservice.items.lost.LostItemService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/lost-items")
public class LostItemController {
    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping()
    Flux<LostItem> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }

    @GetMapping(params = "search")
    Flux<LostItem> searchLostItems(@RequestParam(value = "search", required = true) String searchPhrase) {
        return lostItemService.searchLostItems(searchPhrase);
    }

    @GetMapping("/{id}")
    Mono<LostItem> getLostItemById(@PathVariable("id") String id) {
        return lostItemService.getLostItemById(id);
    }
}
