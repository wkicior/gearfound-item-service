package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import com.gearfound.itemservice.items.found.FoundItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    Flux<FoundItem> searchFoundItems(@RequestParam(value = "search", required = true) String searchPhrase) {
        return foundItemService.searchLostItems(searchPhrase);
    }
}
