package com.reservemanager.user.domain.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String registrationNumber;
    private Boolean active;

    public User(Long id, String name, String email, String registrationNumber, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.active = active;
    }
}
