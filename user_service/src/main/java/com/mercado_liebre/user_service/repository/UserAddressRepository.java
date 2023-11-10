package com.mercado_liebre.user_service.repository;

import com.mercado_liebre.user_service.model.userAddress.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {

    @Query("SELECT ua FROM UserAddress ua WHERE ua.address = :address " +
            "AND ua.addressNumber = :addressNumber " +
            "AND ua.province = :province " +
            "AND ua.city = :city")
    Optional<UserAddress> findByDetails(@Param("address") String address,
                              @Param("addressNumber") int addressNumber,
                              @Param("province") String province,
                              @Param("city") String city);

    @Query("SELECT ua FROM UserAddress ua WHERE ua.user.idUser = :idUser")
    List<UserAddress> findByIdUser(@Param("idUser") Long idUser);
}
