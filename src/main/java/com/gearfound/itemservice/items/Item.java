package com.gearfound.itemservice.items;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Item {
    @Id
    protected String id;
    @TextIndexed(weight=2)
    @NotNull
    protected String name;

    @TextIndexed(weight=3)
    protected String serialNumber;

    @TextIndexed
    private String description;
}
