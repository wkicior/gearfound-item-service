package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.NoAccessToLostItemException;
import com.gearfound.itemservice.items.lost.LostItem;
import com.gearfound.itemservice.items.lost.LostItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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
    Flux<LostItem> searchLostItems(@RequestParam("search") String searchPhrase) {
        return lostItemService.searchLostItems(searchPhrase);
    }

    @GetMapping("/{id}")
    Mono<LostItem> getLostItemById(@PathVariable("id") String id) {
        return lostItemService.getLostItemById(id);
    }

    @GetMapping(params = "registrantId")
    Flux<LostItem> getLostItemsForRegistrantId(@RequestHeader("User-Id") String userId, @RequestParam("registrantId") String registrantId) {
        if (!registrantId.equals(userId)) {
            return Flux.defer(() -> Flux.error(new NoAccessToLostItemException()));
        }
        return lostItemService.getUserLostItems(registrantId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Mono<LostItem> saveLostItem(@RequestBody @Valid LostItem lostItem, @RequestHeader("User-Id") String userId) {
        return lostItemService.save(userId, lostItem);
    }
}
