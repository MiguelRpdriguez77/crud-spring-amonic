package com.adso.crud_mysql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private Boolean active;

    @Column(name = "id_office")
    private Integer idOffice;

    @Column(name = "id_role")
    private Integer idRole;
}