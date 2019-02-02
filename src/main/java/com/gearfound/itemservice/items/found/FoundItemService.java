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

    public Mono<FoundItem> save(String registrantId, FoundItem foundItem) {
        foundItem.setRegistrantId(registrantId);
        return this.foundItemRepository.save(foundItem);
    }

    public Flux<FoundItem> getAllFoundItems() {
        return this.foundItemRepository.findAll();
    }

    public Flux<FoundItem> searchLostItems(String phrase) {
        return this.foundItemRepository.searchFoundItems(phrase);
    }

    public Flux<FoundItem> getUserLostItems(String userId) {
        return this.foundItemRepository.findAllByRegistrantId(userId);
    }
}
