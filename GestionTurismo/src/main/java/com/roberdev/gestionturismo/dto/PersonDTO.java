package com.roberdev.gestionturismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String name;
    private String lastName;
    private String email;
    private String phone;

}
