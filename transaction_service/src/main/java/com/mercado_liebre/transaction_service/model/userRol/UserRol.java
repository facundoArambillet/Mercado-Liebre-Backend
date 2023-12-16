package com.mercado_liebre.transaction_service.model.userRol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado_liebre.transaction_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "userRol")
    @JsonIgnore
    private List<User> users;

}
