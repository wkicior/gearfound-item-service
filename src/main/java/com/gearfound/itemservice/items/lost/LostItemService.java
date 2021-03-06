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

    public Mono<LostItem> save(String registrantId, LostItem lostItem) {
        lostItem.setRegistrantId(registrantId);
        return lostItemRepository.save(lostItem);
    }

    public Flux<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }

    public Flux<LostItem> searchLostItems(String phrase) {
        return lostItemRepository.searchLostItems(phrase);
    }

    public Flux<LostItem> getUserLostItems(String userId) {
        return lostItemRepository.findAllByRegistrantId(userId);
    }

    public Mono<LostItem> getLostItemById(String lostItemId) {
        return lostItemRepository.findById(lostItemId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new LostItemNotFoundException())));
    }

    public Mono<LostItem> edit(String userId, LostItem lostItemInput) {
        return lostItemRepository.findById(lostItemInput.getId()).flatMap(i -> {
            if (!i.getRegistrantId().equals(userId)) {
                return Mono.error(new NoAccessToLostItemException());
            }
            lostItemInput.setRegistrantId(userId);
            return lostItemRepository.save(lostItemInput);
        });
    }
}
