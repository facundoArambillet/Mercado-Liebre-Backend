package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.error.ResponseException;
import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.product.ProductDetailDTO;
import com.mercado_liebre.transaction_service.model.product.ProductMapper;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartDTO;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartMapper;
import com.mercado_liebre.transaction_service.model.shoppingCartHasProduct.ShoppingCartHasProduct;
import com.mercado_liebre.transaction_service.model.user.User;
import com.mercado_liebre.transaction_service.model.user.UserDetailDTO;
import com.mercado_liebre.transaction_service.repository.ShoppingCartHasProductRepository;
import com.mercado_liebre.transaction_service.repository.ShoppingCartRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartHasProductRepository shoppingCartHasProductRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;

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

    public Optional<ShoppingCartDTO> getByIdUser(Long idUser) {
        try {
            Optional<ShoppingCart> shoppingCartFounded = shoppingCartRepository.findByIdUser(idUser);
            if(shoppingCartFounded.isPresent()) {
                ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCartFounded.get());

                return Optional.ofNullable(shoppingCartDTO);
            } else {
                throw new ResponseException("ShoppingCart not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("get by user", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Long> getIdProductByCart(Long idCart) {
        try {
            Optional<ShoppingCart> shoppingCartFounded = shoppingCartRepository.findById(idCart);
            if(shoppingCartFounded.isPresent()) {
                List<Long> idProducts = new ArrayList<>();
                List<ShoppingCartHasProduct> shoppingCartHasProducts = shoppingCartHasProductRepository.findByCart(idCart);
                for(ShoppingCartHasProduct shoppingCartHasProduct : shoppingCartHasProducts) {
                    idProducts.add(shoppingCartHasProduct.getProduct().getIdProduct());
                }
                return idProducts;
            } else {
                throw new ResponseException("ShoppingCart not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("get by user", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private double calculatePrice(Long idCart) {
        List<ShoppingCartHasProduct> shoppingCartHasProducts = shoppingCartHasProductRepository.findByCart(idCart);

        double price = 0;
        for(ShoppingCartHasProduct shoppingCartHasProduct : shoppingCartHasProducts) {
            Long idProduct = shoppingCartHasProduct.getProduct().getIdProduct();
            Optional<ProductDetailDTO> productDetailDTO = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/" + idProduct, ProductDetailDTO.class));
            price += productDetailDTO.get().getPrice();
        }
        return price;
    }
    @Transactional
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        try {
            Long idUser = shoppingCart.getUser().getIdUser();
            Optional<ShoppingCart> ShoppingCartFound = shoppingCartRepository.findByIdUser(idUser);
            if(ShoppingCartFound.isPresent()) {
                throw new ResponseException("Shopping cart already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser, UserDetailDTO.class));
                if (userFound.isPresent()) {
                    User userManaged = entityManager.merge(shoppingCart.getUser());
//                    shoppingCart.setPrice(calculatePrice(shoppingCart.getIdCart()));
                    //Tuve que hacer esto porque sino se me generaba este error:
                    //"detached entity passed to persist"
                    shoppingCart.setUser(userManaged);
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
//    @Transactional
//    public ShoppingCartDTO insertProduct(Long idCart, ProductDetailDTO productDetailDTO) {
//        try {
//            String productName = productDetailDTO.getName();
//            Optional<ProductDetailDTO> productFound = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/name/" + productName, ProductDetailDTO.class));
//            if (productFound.isPresent()) {
//                if(productDetailDTO.getStock() >= 1) {
//                    Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findById(idCart);
//                    if(shoppingCartFound.isPresent()) {
//                        ShoppingCart shoppingCart = shoppingCartFound.get();
//                        Product productInsert = ProductMapper.mapper.productDetailDtoToProduct(productFound.get());
////                            User user = shoppingCart.getUser();
//////                            System.out.println(user.getCreationDate());
////                            shoppingCart.setUser(user);
//                        System.out.println(shoppingCart.getUser());
//                        System.out.println(shoppingCart.getUser().getCreationDate());
//                        shoppingCart.getProducts().add(productInsert);
//                        shoppingCart.setPrice(calculatePrice(shoppingCart));
//                        shoppingCartRepository.save(shoppingCart);
//
//                        ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(shoppingCart);
//                        return shoppingCartDTO;
//                    } else {
//                        throw new ResponseException("Shopping cart not found", null , HttpStatus.NOT_FOUND);
//                    }
//
//                } else {
//                    throw new ResponseException("Insufficient stock", null , HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                throw new ResponseException("Product does not exist", null , HttpStatus.BAD_REQUEST);
//            }
//        } catch (ResponseException ex) {
//            throw ex;
//        } catch (Exception e) {
//            throw new ResponseException("Insert product ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    public ShoppingCartDTO insertProduct(Long idCart, Long idProduct) {
        try {
            Optional<ShoppingCart> cartFounded = shoppingCartRepository.findById(idCart);
            if(cartFounded.isPresent()) {
               Optional<ProductDetailDTO> productFound = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/" + idProduct, ProductDetailDTO.class));
               if(productFound.isPresent()) {
//                   Optional<ShoppingCart> shoppingCartFound = shoppingCartRepository.findByIdUser(idUser);
//                   ShoppingCart shoppingCart = shoppingCartFound.get();
//                   Product product = ProductMapper.mapper.productDetailDtoToProduct(productFound.get());
//                   List<Product> products = shoppingCart.getProducts();
//                   products.add(product);
//                   shoppingCart.setUser(userFounded.get());
//                   shoppingCart.setProducts(products);
//                   shoppingCart.setPrice(calculatePrice(shoppingCart));
                   Product product = ProductMapper.mapper.productDetailDtoToProduct(productFound.get());
                   Optional<ShoppingCartHasProduct> shoppingCartHasProductFound =
                           shoppingCartHasProductRepository.findByCartAndProduct(idCart,idProduct);
                   if(shoppingCartHasProductFound.isPresent()) {
                       throw new ResponseException("The product already exist in the cart", null , HttpStatus.BAD_REQUEST);
                   } else {
                       ShoppingCartHasProduct shoppingCartHasProduct = new ShoppingCartHasProduct();
                       shoppingCartHasProduct.setShoppingCart(cartFounded.get());
                       shoppingCartHasProduct.setProduct(product);
                       shoppingCartHasProductRepository.save(shoppingCartHasProduct);
                       ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(cartFounded.get());
                       return shoppingCartDTO;
                   }

               } else {
                   throw new ResponseException("Product not found", null , HttpStatus.NOT_FOUND);
               }
            } else {
                throw new ResponseException("Shopping cart not found", null , HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Insert product ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ShoppingCartDTO removeProduct(Long idCart, Long idProduct) {
        try {
            Optional<ShoppingCart> cartFounded = shoppingCartRepository.findById(idCart);
            if(cartFounded.isPresent()) {
                Optional<ProductDetailDTO> productFound = Optional.ofNullable(restTemplate.getForObject("http://product-service/product/" + idProduct, ProductDetailDTO.class));
                if(productFound.isPresent()) {
                    Optional<ShoppingCartHasProduct> shoppingCartHasProductFound =
                            shoppingCartHasProductRepository.findByCartAndProduct(idCart,idProduct);
                    if(shoppingCartHasProductFound.isPresent()) {
                        ShoppingCartHasProduct shoppingCartHasProduct = shoppingCartHasProductFound.get();
                        shoppingCartHasProductRepository.delete(shoppingCartHasProduct);
                        ShoppingCartDTO shoppingCartDTO = ShoppingCartMapper.mapper.shoppingCartToShoppingCartDto(cartFounded.get());
                        return shoppingCartDTO;
                    } else {
                        throw new ResponseException("The product not found in the cart", null , HttpStatus.BAD_REQUEST);
                    }

                } else {
                    throw new ResponseException("Product not found", null , HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ResponseException("Shopping cart not found", null , HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Insert product ShoppingCart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
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
