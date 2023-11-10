package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.model.userRol.UserRolMapper;
import com.mercado_liebre.user_service.repository.UserRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRolServiceImpl implements UserRolService{

    @Autowired
    private UserRolRepository userRolRepository;

    public List<UserRolDTO> getAll() {
        try {
            List<UserRol> userRoles = userRolRepository.findAll();
            List<UserRolDTO> userRolDTOS = userRoles.stream().map(
                    rol -> UserRolMapper.mapper.userRolToUserRolDto(rol)).collect(Collectors.toList());

            return userRolDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<UserRolDTO> getById(Long idRol) {
        try {
            Optional<UserRol> userRolFound = userRolRepository.findById(idRol);
            if (userRolFound.isPresent()) {
                UserRol userRol = userRolFound.get();
                UserRolDTO userRolDTO = UserRolMapper.mapper.userRolToUserRolDto(userRol);

                return Optional.ofNullable(userRolDTO);
            } else {
                throw new ResponseException("User rol not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching userRol", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<UserRol> getByType(String type) {
        try {
            Optional<UserRol> userRolFound = userRolRepository.findByType(type);
            if(userRolFound.isPresent()) {
                return userRolFound;
            } else {
                throw new ResponseException("User rol not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Type userRol", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserRolDTO createUserRol(UserRolDTO userRolDTO) {
        try {
            Optional<UserRol> userRolFound = userRolRepository.findByType(userRolDTO.getType());
            if(userRolFound.isPresent()) {
                throw new ResponseException("User rol already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                UserRol userRol = UserRolMapper.mapper.userRolDtoToUserRol(userRolDTO);
                userRolRepository.save(userRol);

                return userRolDTO;
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create userRol", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public UserRolDTO updateUserRol(Long idRol, UserRolDTO userRolDTO) {
        try {
            Optional<UserRol> userRolFound = userRolRepository.findById(idRol);
            if(userRolFound.isPresent()) {
                UserRol userRolUpdated = userRolFound.get();
                userRolUpdated.setType(userRolDTO.getType());
                userRolRepository.save(userRolUpdated);
                return userRolDTO;
            } else {
                throw new ResponseException("That user rol does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update userRol", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public UserRolDTO deleteUserRol(Long idRol) {
        try {
            Optional<UserRol> userRolFound = userRolRepository.findById(idRol);
            if(userRolFound.isPresent()) {
                UserRol userRolDelete = userRolFound.get();
                UserRolDTO userRolDTO = UserRolMapper.mapper.userRolToUserRolDto(userRolDelete);
                userRolRepository.delete(userRolDelete);
                return userRolDTO;
            } else {
                throw new ResponseException("That user rol does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete userRol", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
