package com.gearfound.itemservice.items.found;

import reactor.core.publisher.Flux;

interface FoundItemMongoTemplateRepository {
    Flux<FoundItem> searchFoundItems(String some_phrase);}
