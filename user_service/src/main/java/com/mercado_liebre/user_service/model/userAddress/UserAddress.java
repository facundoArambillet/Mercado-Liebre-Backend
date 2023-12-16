package com.mercado_liebre.user_service.model.userAddress;

import com.mercado_liebre.user_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long idAddress;
    @Column(nullable = false)
    private String address;
    @Column(name = "address_number", nullable = false)
    private int addressNumber;
    @Column(nullable = false)
    private String province;
    @Column(nullable = false)
    private String city;
    @Column(name = "postal_code", nullable = false)
    private int postalCode;
    @Column(name = "contact_phone", nullable = false)
    private BigInteger contactPhone;
    @Column(name = "is_principal", nullable = false)
    private boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name= "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_user_address_users",
            foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

}
