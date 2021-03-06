package com.gearfound.itemservice.items.found;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FoundItemRepository extends ReactiveMongoRepository<FoundItem, String>, FoundItemMongoTemplateRepository {
    Flux<FoundItem> findAllByRegistrantId(String userId);
}
