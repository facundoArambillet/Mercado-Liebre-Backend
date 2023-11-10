package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.error.ResponseException;
import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.product.ProductDetailDTO;
import com.mercado_liebre.transaction_service.model.product.ProductMapper;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartDTO;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartMapper;
import com.mercado_liebre.transaction_service.model.user.User;
import com.mercado_liebre.transaction_service.model.user.UserDetailDTO;
import com.mercado_liebre.transaction_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<ShoppingCartDTO> getAll() {
        try {
            List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
            List<ShoppingCartDTO> shoppingCartDTOS = shoppingCarts.stream().map(
                    shoppingCart -> ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCart)).collect(Collectors.toList());

            return shoppingCartDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<ShoppingCartDTO> getById(Long idShoppingCart) {
        try {
            Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
            if (shoppingCartFound.isPresent()) {
                ShoppingCart shoppingCart = shoppingCartFound.get();
                ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCart);

                return Optional.ofNullable(shoppingCartDTO);
            } else {
                throw new ResponseException("ShoppingCart not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching ShoppingCart", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private double calculatePrice(ShoppingCart shoppingCart) {
        double price = 0;
        for(Product product : shoppingCart.getProducts()) {
            price += product.getPrice();
        }
        return price;
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        try {
            Long idUser = shoppingCart.getUser().getIdUser();
            Optional<ShoppingCart> ShoppingCartFound = shoppingCartRepository.findByIdUser(idUser);
            if(ShoppingCartFound.isPresent()) {
                throw new ResponseException("Shopping cart already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Optional<User> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser, User.class));
                if (userFound.isPresent()) {
                    shoppingCart.setPrice(calculatePrice(shoppingCart));
                    return shoppingCartRepository.save(shoppingCart);
                } else {
                    throw new ResponseException("User does not exist", null , HttpStatus.BAD_REQUEST);
                }

            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    public ShoppingCart updateShoppingCart(Long idShoppingCart, ShoppingCart shoppingCart) {
//        try {
//            Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
//            if(shoppingCartFound.isPresent()) {
//                Long idUser = shoppingCart.getUser().getIdUser();
//                Optional<User> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser, User.class));
//                if(userFound.isPresent()) {
//                    ShoppingCart shoppingCartUpdated = shoppingCartFound.get();
//                    shoppingCartUpdated.setUser(shoppingCart.getUser());
//                    shoppingCartUpdated.setProducts(shoppingCart.getProducts());
//                    return shoppingCartRepository.save(shoppingCartUpdated);
//                } else {
//                    throw new ResponseException("ShoppingCart rol does not exist", null, HttpStatus.BAD_REQUEST);
//                }
//
//            } else {
//                throw new ResponseException("That ShoppingCart does not exist ", null, HttpStatus.NOT_FOUND);
//            }
//        }  catch (ResponseException ex) {
//            throw ex;
//        }  catch (Exception e) {
//            throw new ResponseException("Update ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    public ShoppingCartDTO insertProduct(ProductDetailDTO productDetailDTO, Long idUser) {
        try {
            Optional<User> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser, User.class));
            if (userFound.isPresent()) {
                String productName = productDetailDTO.getName();
                Optional<ProductDetailDTO> productFound = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/name/" + productName, ProductDetailDTO.class));
                if (productFound.isPresent()) {
                    if(productDetailDTO.getStock() >= 1) {
                        Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findByIdUser(idUser);
                        //No hago condicion para ver si existe el shoppingCart, ya que cuando se crea un usuario
                        //automaticamente creo un carrito de compras
                        ShoppingCart shoppingCart = shoppingCartFound.get();
                        Product productInsert = ProductMapper.mapper.productDetailDtoToProduct(productFound.get());
                        shoppingCart.getProducts().add(productInsert);
                        shoppingCart.setPrice(calculatePrice(shoppingCart));
                        shoppingCartRepository.save(shoppingCart);

                        ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCart);
                        return shoppingCartDTO;
                    } else {
                        throw new ResponseException("Insufficient stock", null , HttpStatus.BAD_REQUEST);
                    }
                } else {
                    throw new ResponseException("Product does not exist", null , HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("User does not exist", null , HttpStatus.BAD_REQUEST);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Insert product ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ShoppingCartDTO removeProduct(ProductDetailDTO productDetailDTO, Long idUser) {
        try {
            Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser, UserDetailDTO.class));
            if(userFound.isPresent()) {
                String productName = productDetailDTO.getName();
                Optional<ProductDetailDTO> productFound = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/name/" + productName, ProductDetailDTO.class));
                if(productFound.isPresent()) {
                    Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findByIdUser(idUser);
                    ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCartFound.get());
                    ProductDetailDTO productRemoved = productFound.get();
                    shoppingCartDTO.getProducts().remove(productRemoved);

                    ShoppingCart shoppingCartMapped = ShoppingCartMapper.mapper.shoppingCartDtoToShoppingCart(shoppingCartDTO);
                    shoppingCartMapped.setPrice(calculatePrice(shoppingCartMapped));

                    shoppingCartRepository.save(shoppingCartMapped);
                    return shoppingCartDTO;
                } else {
                    throw new ResponseException("Product does not exist", null , HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new ResponseException("User does not exist", null , HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete product ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ShoppingCartDTO deleteShoppingCart(Long idShoppingCart) {
        try {
            Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idShoppingCart);
            if(shoppingCartFound.isPresent()) {
                ShoppingCart shoppingCartDelete = shoppingCartFound.get();
                ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCartDelete);
                shoppingCartRepository.delete(shoppingCartDelete);

                return shoppingCartDTO;
            } else {
                throw new ResponseException("That ShoppingCart does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}