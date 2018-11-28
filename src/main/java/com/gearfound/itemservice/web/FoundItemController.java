package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import com.gearfound.itemservice.items.found.FoundItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class FoundItemController {

    private final FoundItemService foundItemService;

    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    @PostMapping("/found-items")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<FoundItem> saveFoundItem(@RequestBody @Valid FoundItem foundItem) {
        return foundItemService.save(foundItem);
    }

    @GetMapping("/found-items")
    Flux<FoundItem> getAllFoundItems() {
        return foundItemService.getAllFoundItems();
    }

    @GetMapping(value = "/found-items", params = "search")
    Flux<FoundItem> searchFoundItems(@RequestParam(value = "search", required = true) String searchPhrase) {
        return foundItemService.searchLostItems(searchPhrase);
    }
}
