package com.gearfound.itemservice.items.lost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LostItemService {
    private final LostItemRepository lostItemRepository;

    @Autowired
    public LostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    public Mono<LostItem> save(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }

    public Flux<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }
}