package com.backend.ecom.entities;

import com.backend.ecom.supporters.RoleType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;

@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

    public Role (RoleType name){
        this.name = name;
    }
}
