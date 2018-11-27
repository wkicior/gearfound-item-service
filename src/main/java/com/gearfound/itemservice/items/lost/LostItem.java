package com.gearfound.itemservice.items.lost;


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
public class LostItem extends Item {
    private String lostPlace;
    @Past
    private Date lostDate;
}
