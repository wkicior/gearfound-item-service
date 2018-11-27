package com.gearfound.itemservice.items.lost;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostItemRepository extends ReactiveMongoRepository<LostItem, String> {

}
