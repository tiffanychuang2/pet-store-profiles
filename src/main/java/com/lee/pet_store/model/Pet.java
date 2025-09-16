package com.lee.pet_store.model;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private String petType;
    private String petName;
}
