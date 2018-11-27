package com.gearfound.itemservice.lost;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@Document
@EqualsAndHashCode
public class LostItem {
    @Id
    private String id;
    @NotNull
    private String name;
    private String serialNumber;
    private String lostPlace;
    @Past
    private Date lostDate;
    private String description;
}
