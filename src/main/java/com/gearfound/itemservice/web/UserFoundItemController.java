package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.found.FoundItem;
import com.gearfound.itemservice.items.found.FoundItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/found-items")
public class UserFoundItemController {
    private final FoundItemService foundItemService;

    public UserFoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Mono<FoundItem> saveFoundItem(@RequestBody @Valid FoundItem foundItem, @RequestHeader("User-Id") String userId) {
        return foundItemService.save(userId, foundItem);
    }

    @GetMapping()
    Flux<FoundItem> getUserFoundItems(@RequestHeader("User-Id") String userId) {
        return foundItemService.getUserLostItems(userId);
    }
}
