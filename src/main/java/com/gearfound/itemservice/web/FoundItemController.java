package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.NoAccessToFoundItemException;
import com.gearfound.itemservice.items.found.FoundItem;
import com.gearfound.itemservice.items.found.FoundItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/found-items")
public class FoundItemController {

    private final FoundItemService foundItemService;

    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    @GetMapping()
    Flux<FoundItem> getAllFoundItems() {
        return foundItemService.getAllFoundItems();
    }

    @GetMapping(params = "search")
    Flux<FoundItem> searchFoundItems(@RequestParam("search") String searchPhrase) {
        return foundItemService.searchLostItems(searchPhrase);
    }

    @GetMapping("/{id}")
    Mono<FoundItem> getLostItemById(@PathVariable("id") String id) {
        return foundItemService.getFoundItemById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Mono<FoundItem> saveFoundItem(@RequestBody @Valid FoundItem foundItem, @RequestHeader("User-Id") String userId) {
        return foundItemService.save(userId, foundItem);
    }

    @GetMapping(params = "registrantId")
    Flux<FoundItem> getFoundItemsForRegistrant(@RequestHeader("User-Id") String userId, @RequestParam("registrantId") String registrantId) {
        if (!registrantId.equals(userId)) {
            return Flux.defer(() -> Flux.error(new NoAccessToFoundItemException()));
        }
        return foundItemService.getUserLostItems(userId);
    }
}
