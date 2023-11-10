package com.mercado_liebre.user_service.model.userAddress;

import com.mercado_liebre.user_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String address;
    @Column(name = "address_number")
    private int addressNumber;
    private String province;
    private String city;
    @Column(name = "postal_code")
    private int postalCode;
    @Column(name = "contact_phone")
    private int contactPhone;
    @Column(name = "is_principal")
    private boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

}
