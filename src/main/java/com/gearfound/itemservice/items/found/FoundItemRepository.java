package com.gearfound.itemservice.items.found;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundItemRepository  extends ReactiveMongoRepository<FoundItem, String> {
    //
}
