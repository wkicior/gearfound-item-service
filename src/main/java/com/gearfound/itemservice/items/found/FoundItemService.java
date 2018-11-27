package com.gearfound.itemservice.items.found;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;

    @Autowired
    public FoundItemService(FoundItemRepository foundItemRepository) {
        this.foundItemRepository = foundItemRepository;
    }

    public Mono<FoundItem> save(FoundItem foundItem) {
        return this.foundItemRepository.save(foundItem);
    }

    public Flux<FoundItem> getAllFoundItems() {
        return this.foundItemRepository.findAll();
    }
}
