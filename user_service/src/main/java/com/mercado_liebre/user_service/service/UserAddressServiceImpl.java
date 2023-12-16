package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.model.user.UserDTO;
import com.mercado_liebre.user_service.model.userAddress.*;
import com.mercado_liebre.user_service.repository.UserAddressRepository;
import com.mercado_liebre.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private UserRepository userRepository;

    public List<UserAddressDTO> getAll() {
        try {
            List<UserAddress> userAddresses = userAddressRepository.findAll();
            List<UserAddressDTO> userAddressDTOS = userAddresses.stream().map(
                    userAddress -> UserAddressMapper.mapper.userAddressToUserAddressDto(userAddress)).collect(Collectors.toList());

            return userAddressDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserAddressDetailDTO> getById(Long idAddress) {
        try {
            Optional<UserAddress> userAddressFound = userAddressRepository.findById(idAddress);
            if (userAddressFound.isPresent()) {
                UserAddress userAddress = userAddressFound.get();
                UserAddressDetailDTO userAddressDetailDTO = UserAddressMapper.mapper.userAddressToUserAddressDetailDto(userAddress);

                return Optional.ofNullable(userAddressDetailDTO);
            } else {
                throw new ResponseException("User Address not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching user address", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<UserAddressDetailDTO> getByUser(Long idUser) {
        try {
            Optional<User> userFounded = userRepository.findById(idUser);
            if(userFounded.isPresent()) {
                List<UserAddress> adresses = userAddressRepository.findByIdUser(idUser);
                List<UserAddressDetailDTO> addressDetailDTOS = adresses.stream().map(
                        userAddress -> UserAddressMapper.mapper.userAddressToUserAddressDetailDto(userAddress)).collect(Collectors.toList());

                return addressDetailDTOS;
            } else {
                throw new ResponseException("User not founded", null, HttpStatus.NOT_FOUND);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error get by user", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserAddressCreateDTO createUserAddress(UserAddressCreateDTO userAddressCreateDTO) {
        try {
            Optional<UserAddress> userAddressFound = userAddressRepository.findByDetails(userAddressCreateDTO.getAddress(),
                    userAddressCreateDTO.getAddressNumber(), userAddressCreateDTO.getProvince(), userAddressCreateDTO.getCity()
            );
            if(userAddressFound.isPresent()) {
                throw new ResponseException("User Address already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Long idUser = userAddressCreateDTO.getUser().getIdUser();
                Optional<User> userFound = userRepository.findById(idUser);
                if(userFound.isPresent()) {
                    UserAddress userAddress = UserAddressMapper.mapper.userAddressCreateDtoToUserAddress(userAddressCreateDTO);
                    User user = userFound.get();
                    List<UserAddress> userAddresses = userAddressRepository.findByIdUser(user.getIdUser());
                    if (userAddresses.isEmpty()) {
                        userAddress.setPrincipal(true);
                    } else {
                        userAddress.setPrincipal(false);
                    }

                    userAddressRepository.save(userAddress);

                    return userAddressCreateDTO;
                } else {
                    throw new ResponseException("User does not exist", null, HttpStatus.BAD_REQUEST);
                }

            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create user address", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public UserAddressDetailDTO updateUserAddress(Long idAddress, UserAddressDetailDTO userAddressDetailDTO) {
        try {
            Optional<UserAddress> userAddressFound = userAddressRepository.findById(idAddress);
            if(userAddressFound.isPresent()) {
                Long idUser = userAddressDetailDTO.getUser().getIdUser();
                Optional<User> userFound = userRepository.findById(idUser);
                if(userFound.isPresent()) {
                    UserAddress userAddressUpdated = userAddressFound.get();
                    userAddressUpdated.setAddress(userAddressDetailDTO.getAddress());
                    userAddressUpdated.setAddressNumber(userAddressDetailDTO.getAddressNumber());
                    userAddressUpdated.setProvince(userAddressDetailDTO.getProvince());
                    userAddressUpdated.setCity(userAddressDetailDTO.getCity());
                    userAddressUpdated.setPostalCode(userAddressDetailDTO.getPostalCode());
                    userAddressUpdated.setContactPhone(userAddressDetailDTO.getContactPhone());
                    userAddressUpdated.setPrincipal(userAddressDetailDTO.isPrincipal());
                    userAddressRepository.save(userAddressUpdated);

                    return userAddressDetailDTO;
                } else {
                    throw new ResponseException("User  does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That user address does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update user address", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public UserAddressDetailDTO toogleAddressPrincipal(UserAddressDetailDTO userAddressDetailDTO) {
        try {
            Long idUser = userAddressDetailDTO.getUser().getIdUser();
            Optional<User> userFound = userRepository.findById(idUser);
            if(userFound.isPresent()) {
                Optional<UserAddress> userAddressFound = userAddressRepository.findById(userAddressDetailDTO.getIdAddress());
                if (userAddressFound.isPresent()) {
                    User user = userFound.get();
                    UserAddress userAddressPrincipal = userAddressFound.get();
                    List<UserAddress> userAddresses = userAddressRepository.findByIdUser(user.getIdUser());
                    userAddresses.forEach(userAddress -> userAddress.setPrincipal(false));
                    userAddressPrincipal.setPrincipal(true);

                    userAddressRepository.saveAll(userAddresses);
                    userAddressRepository.save(userAddressPrincipal);

                    return userAddressDetailDTO;
                } else {
                    throw new ResponseException("User Address does not exist", null, HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new ResponseException("User does not exist", null, HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Toogle user address principal", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserAddressDTO deleteUserAddress(Long idAddress) {
        try {
            Optional<UserAddress> userAddressFound = userAddressRepository.findById(idAddress);
            if(userAddressFound.isPresent()) {
                UserAddress userAddressDelete = userAddressFound.get();
                UserAddressDTO userAddressDTO = UserAddressMapper.mapper.userAddressToUserAddressDto(userAddressDelete);
                userAddressRepository.delete(userAddressDelete);

                return userAddressDTO;
            } else {
                throw new ResponseException("That user address does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete user address", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
