package com.gearfound.itemservice.items;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Item {
    @Id
    protected String id;
    @NotNull
    protected String name;
    protected String serialNumber;
    private String description;
}
