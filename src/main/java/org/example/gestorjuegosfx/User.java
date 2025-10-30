package org.example.gestorjuegosfx;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private String email;
    private String password;
    private Boolean is_admin;
}
