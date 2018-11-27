package com.gearfound.itemservice.items.found;

import com.gearfound.itemservice.items.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@Document
@EqualsAndHashCode(callSuper = true)
public class FoundItem extends Item {
    private String foundPlace;
    @Past
    private Date foundDate;
}
