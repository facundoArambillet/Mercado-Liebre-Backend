package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.error.ResponseException;
import com.mercado_liebre.user_service.model.category.Category;
import com.mercado_liebre.user_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.user_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.user_service.model.categoryFamily.CategoryFamilyMapper;
import com.mercado_liebre.user_service.model.product.Product;
import com.mercado_liebre.user_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.user_service.model.user.*;
import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.repository.UserRepository;
import com.mercado_liebre.user_service.repository.UserRolRepository;
import com.mercado_liebre.user_service.security.JwtProvider;
import com.mercado_liebre.user_service.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolRepository userRolRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAll() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOS = users.stream().map(
                    user -> UserMapper.mapper.userToUserDto(user)).collect(Collectors.toList());

            return userDTOS;

        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<UserDetailDTO> getById(Long idUser) {
        try {
            Optional<User> userFound = userRepository.findById(idUser);
            if (userFound.isPresent()) {
                User user = userFound.get();
                UserDetailDTO userDetailDTO = UserMapper.mapper.userToUserDetailDto(user);

                return Optional.ofNullable(userDetailDTO);
            } else {
                throw new ResponseException("User not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching user", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Optional<User> getByIdUser(Long idUser) {
        try {
            Optional<User> userFound = userRepository.findById(idUser);
            if (userFound.isPresent()) {

                return userFound;
            } else {
                throw new ResponseException("User not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching user", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public  Optional<UserDetailDTO> getByEmail(String email) {
        try {
            Optional<User> userFound = userRepository.findByEmail(email);
            if(userFound.isPresent()) {
                User user = userFound.get();
                UserDetailDTO userDetailDTO = UserMapper.mapper.userToUserDetailDto(user);

                return Optional.ofNullable(userDetailDTO);
            } else {
                throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Email user", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    public List<CategoryFamilyDTO> getLatestProductsByCategoryFamilyInHistoryById(Long idUser) {
//        try {
//            Optional<User> userFounded = userRepository.findById(idUser);
//            if (userFounded.isPresent()) {
//                List<Product> userProducts = this.getUserProductsById(idUser);
//                if(!userProducts.isEmpty()) {
////                    Category latestProductCategoryHistory = userProducts.get(userProducts.size() - 1).getCategory();
////                    CategoryFamily latestCategoryFamilyHistory = latestProductCategoryHistory.getCategoryFamily();
//                    CategoryFamilyDTO categoryFamilyDTO = restTemplate.getForObject("http://product-service/category-family/latest" + idUser , CategoryFamilyDTO.class);
//                    List<CategoryFamilyDTO> arrayList = new ArrayList<>();
//                    arrayList.add(categoryFamilyDTO);
//                    return arrayList;
//                }
//
//                return new ArrayList<>();
//            } else {
//                throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
//            }
//        } catch (ResponseException ex) {
//            throw ex;
//        } catch (Exception e) {
//            throw new ResponseException("Get By latest category family user", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    private List<Product> getUserProductsById(Long idUser) {
//        try {
//            Optional<User> userFounded = userRepository.findById(idUser);
//            if(userFounded.isPresent()) {
//                User user = userFounded.get();
//                return user.getProducts();
//            } else {
//                throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
//            }
//        } catch (ResponseException ex) {
//            throw ex;
//        } catch (Exception e) {
//            throw new ResponseException("Get user products by id user", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public boolean validateEmail(String email) {
        // Expresión regular para validar el formato del correo electrónico
        String regex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.(com|es|org)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        // Verificar si el formato del correo electrónico es válido
        if (!matcher.matches()) {
            return false;
        }

        // Verificar el dominio permitido
        String[] allowedDomains = {"gmail", "outlook", "hotmail"};
        String[] parts = email.split("@");

        String domain = parts[1].toLowerCase();
        for (String allowedDomain : allowedDomains) {
            if (domain.contains(allowedDomain)) {
                return true;
            }
        }

        return false;
    }
    public UserCreateDTO registerUser(UserCreateDTO userCreateDTO) {
        try {
            if(this.validateEmail(userCreateDTO.getEmail())) {
                userCreateDTO.setEmail(userCreateDTO.getEmail().toLowerCase());
                Optional<User> userFound = userRepository.findByEmail(userCreateDTO.getEmail());
                if(userFound.isPresent()) {
                    throw new ResponseException("User already exist", null, HttpStatus.BAD_REQUEST);
                } else {
                    Optional<UserRol> userRolFound = userRolRepository.findByType("BASIC");
                    if(userRolFound.isPresent()) {
                        User user = UserMapper.mapper.userCreationDtoToUser(userCreateDTO);
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        ShoppingCart shoppingCart = new ShoppingCart();
                        List<Product> products = new ArrayList<>();

                        user.setCreationDate(Date.valueOf(LocalDate.now()));
                        user.setSalesMade(0L);
                        user.setUserRol(userRolFound.get());

                        shoppingCart.setPrice(0);
                        shoppingCart.setUser(user);

                        userRepository.save(user);
                        restTemplate.postForObject("http://transaction-service/shopping-cart",shoppingCart, ShoppingCart.class);

                        return userCreateDTO;
                    } else {
                        throw new ResponseException("User rol does not exist", null, HttpStatus.BAD_REQUEST);
                    }

                }
            } else {
                throw new ResponseException("Invalid email format", null, HttpStatus.BAD_REQUEST);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create User", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Token login(UserLoginDTO userLoginDTO) {
        try {
            Optional<User> userFound = userRepository.findByEmail(userLoginDTO.getEmail());
            if(userFound.isPresent()) {
                User user = userFound.get();
                if(passwordEncoder.matches(userLoginDTO.getPassword(),user.getPassword())) {
                    UserDTO userDTO = UserMapper.mapper.userToUserDto(user);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
                    Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                    UserDetailsImpl userToken = (UserDetailsImpl) authentication.getPrincipal();
                    String token = jwtProvider.createToken(userToken);

                    return new Token(token);
                } else {
                    throw new ResponseException("Password incorrect", null, HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new ResponseException("Email incorrect", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Login User", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User updateUser(Long idUser, User user) {
        try {
            Optional<User> userFound = userRepository.findById(idUser);
            if(userFound.isPresent()) {
                Long idUserRol = user.getUserRol().getIdRol();
                Optional<UserRol> userRolFound = userRolRepository.findById(idUserRol);
                if(userRolFound.isPresent()) {
                    User userUpdated = userFound.get();
                    userUpdated.setEmail(user.getEmail());
                    userUpdated.setPassword(user.getPassword());
                    userUpdated.setName(user.getName());
                    userUpdated.setLastName(user.getLastName());
                    userUpdated.setCreationDate(user.getCreationDate());
                    userUpdated.setSalesMade(user.getSalesMade());
                    userUpdated.setUserRol(user.getUserRol());

                    return userRepository.save(userUpdated);
                } else {
                    throw new ResponseException("User rol does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That user does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update User", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //Estoy tomando como base que una venta es cuando una persona compra(sin importar cuantas catidades compre)
    //Por eso sumo de a 1
    public UserDTO incrementSalesMade(Long idUser) {
        try {
            Optional<User> userFound = userRepository.findById(idUser);
            if(userFound.isPresent()) {
                User user = userFound.get();
                user.setSalesMade(user.getSalesMade() + 1);
                UserDTO userDTO = UserMapper.mapper.userToUserDto(user);
                userRepository.save(user);

                return userDTO;
            } else {
                throw new ResponseException("That user does not exist ", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Update User", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserDTO deleteUser(Long idUser) {
        try {
            Optional<User> userFound = userRepository.findById(idUser);
            if(userFound.isPresent()) {
                User userDelete = userFound.get();
                UserDTO userDTO = UserMapper.mapper.userToUserDto(userDelete);
                userRepository.delete(userDelete);

                return userDTO;
            } else {
                throw new ResponseException("That user does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete User", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
