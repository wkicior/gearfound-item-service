package com.gearfound.itemservice.web;

import com.gearfound.itemservice.items.lost.LostItem;
import com.gearfound.itemservice.items.lost.LostItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/lost-items")
public class UserLostItemController {
    private final LostItemService lostItemService;

    public UserLostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping()
    Flux<LostItem> getUserLostItems(@RequestHeader("User-Id") String userId) {
        return lostItemService.getUserLostItems(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Mono<LostItem> saveLostItem(@RequestBody @Valid LostItem lostItem, @RequestHeader("User-Id") String userId) {
        return lostItemService.save(userId, lostItem);
    }
}
