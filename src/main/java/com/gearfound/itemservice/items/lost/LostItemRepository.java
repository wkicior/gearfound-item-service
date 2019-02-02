package com.gearfound.itemservice.items.lost;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LostItemRepository extends ReactiveMongoRepository<LostItem, String>, LostItemMongoTemplateRepository {
    Flux<LostItem> findAllByRegistrantId(String userId);
}
