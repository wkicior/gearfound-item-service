package com.gearfound.itemservice.items.lost;

import reactor.core.publisher.Flux;

public interface LostItemMongoTemplateRepository {
    Flux<LostItem> searchLostItems(String phrase);
}
