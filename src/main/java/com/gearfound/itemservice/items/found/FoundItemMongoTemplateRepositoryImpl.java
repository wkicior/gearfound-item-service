package com.gearfound.itemservice.items.found;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import reactor.core.publisher.Flux;

public class FoundItemMongoTemplateRepositoryImpl implements FoundItemMongoTemplateRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    public FoundItemMongoTemplateRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<FoundItem> searchFoundItems(String phrase) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny(phrase);
        Query query = TextQuery.queryText(criteria)
                .sortByScore();
        return mongoTemplate.find(query, FoundItem.class);
    }
}
