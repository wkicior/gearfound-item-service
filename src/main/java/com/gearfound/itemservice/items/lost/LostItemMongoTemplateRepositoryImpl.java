package com.gearfound.itemservice.items.lost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import reactor.core.publisher.Flux;

public class LostItemMongoTemplateRepositoryImpl implements LostItemMongoTemplateRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public LostItemMongoTemplateRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<LostItem> searchLostItems(String phrase) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny(phrase);

        Query query = TextQuery.queryText(criteria)
                .sortByScore();
        return mongoTemplate.find(query, LostItem.class);
    }
}
