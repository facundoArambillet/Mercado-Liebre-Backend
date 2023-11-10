package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeDTO;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeMapper;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.repository.RolAttributeRepository;
import com.mercado_liebre.user_service.repository.UserRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolAttributeServiceImpl implements RolAttributeService {

    @Autowired
    private RolAttributeRepository rolAttributeRepository;
    @Autowired
    private UserRolRepository userRolRepository;

    public List<RolAttributeDTO> getAll() {
        try {
            List<RolAttribute> rolAttributes = rolAttributeRepository.findAll();
            List<RolAttributeDTO> rolAttributeDTOS = rolAttributes.stream().map(
                    rolAttribute -> RolAttributeMapper.mapper.rolAttributeToRolAttributeDto(rolAttribute)).collect(Collectors.toList());

            return rolAttributeDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<RolAttribute> getById(Long idAttribute) {
        try {
            Optional<RolAttribute> rolAttributeFound = rolAttributeRepository.findById(idAttribute);
            if (rolAttributeFound.isPresent()) {
                return rolAttributeFound;
            } else {
                throw new ResponseException("Rol attribute not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching rolAttribute", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<RolAttribute> getByName(String name) {
        try {
            Optional<RolAttribute> rolAttributeFound = rolAttributeRepository.findByName(name);
            if(rolAttributeFound.isPresent()) {
                return rolAttributeFound;
            } else {
                throw new ResponseException("Rol attribute not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Name rolAttribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public RolAttribute createRolAttribute(RolAttribute rolAttribute) {
        try {
            Optional<RolAttribute> rolAttributeFound = rolAttributeRepository.findByName(rolAttribute.getName());
            if(rolAttributeFound.isPresent()) {
                throw new ResponseException("Rol attribute already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                String typeUserRol  = rolAttribute.getUserRol().getType();
                Optional<UserRol> userRolFound = userRolRepository.findByType(typeUserRol);
                if(userRolFound.isPresent()) {

                    return rolAttributeRepository.save(rolAttribute);
                } else {
                    throw new ResponseException("User rol does not exist", null, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create rolAttribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public RolAttributeDTO updateRolAttribute(Long idAttribute, RolAttributeDTO rolAttributeDTO) {
        try {
            Optional<RolAttribute> rolAttributeFound = rolAttributeRepository.findById(idAttribute);
            if(rolAttributeFound.isPresent()) {
                String typeUserRol  = rolAttributeDTO.getUserRol().getType();
                Optional<UserRol> userRolFound = userRolRepository.findByType(typeUserRol);
                if(userRolFound.isPresent()) {
                    RolAttribute rolAttributeUpdated = rolAttributeFound.get();
                    rolAttributeUpdated.setName(rolAttributeDTO.getName());
                    rolAttributeUpdated.setValue(rolAttributeDTO.getValue());
                    rolAttributeRepository.save(rolAttributeUpdated);

                    return rolAttributeDTO;
                } else {
                    throw new ResponseException("User rol does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That rol attribute does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update rolAttribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public RolAttributeDTO deleteRolAttribute(Long idAttribute) {
        try {
            Optional<RolAttribute> rolAttributeFound = rolAttributeRepository.findById(idAttribute);
            if(rolAttributeFound.isPresent()) {
                RolAttribute rolAttributeDelete = rolAttributeFound.get();
                RolAttributeDTO rolAttributeDTO = RolAttributeMapper.mapper.rolAttributeToRolAttributeDto(rolAttributeDelete);
                rolAttributeRepository.delete(rolAttributeDelete);
                return rolAttributeDTO;
            } else {
                throw new ResponseException("That rol attribute does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete rol attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
