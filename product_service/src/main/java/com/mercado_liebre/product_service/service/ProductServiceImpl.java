package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentMapper;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentPlanDTO;
import com.mercado_liebre.product_service.model.product.*;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.user.User;
import com.mercado_liebre.product_service.model.user.UserDetailDTO;
import com.mercado_liebre.product_service.model.user.UserMapper;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import com.mercado_liebre.product_service.repository.ProductAttributeRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<ProductDTO> getAll() {
        try {
            List<Product> products = productRepository.findAll();
            List<ProductDTO> productDTOS = products.stream().map(
                    product -> ProductMapper.mapper.productToProductDto(product)).collect(Collectors.toList());

            return productDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<ProductDetailDTO> getById(Long idProduct) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if (productFound.isPresent()) {
                Product product = productFound.get();
                ProductDetailDTO productDetailDTO = ProductMapper.mapper.productToProductDetailDto(product);

                return Optional.ofNullable(productDetailDTO);
            } else {
                throw new ResponseException("Product not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching product", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<ProductDetailDTO> getByName(String name) {
        try {
            Optional<Product> productFound = productRepository.findByName(name);
            if(productFound.isPresent()) {
                Product product = productFound.get();
                ProductDetailDTO productDetailDTO = ProductMapper.mapper.productToProductDetailDto(product);

                return Optional.ofNullable(productDetailDTO);
            } else {
                throw new ResponseException("Product not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Name product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  Optional<ProductDTO> getByNameAndInsertIntoHistory(String name, Long idUser) {
        try {
            Optional<Product> productFound = productRepository.findByName(name);
            if(productFound.isPresent()) {
                Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser , UserDetailDTO.class));
                if (userFound.isPresent()) {
                    Product product = productFound.get();
                    ProductDTO productDTO = ProductMapper.mapper.productToProductDto(product);
                    UserDetailDTO userDetailDTO = userFound.get();
                    User user = UserMapper.mapper.userDetailDtoToUser(userDetailDTO);
                    product.getUsers().add(user);
                    productRepository.save(product);

                    return Optional.ofNullable(productDTO);
                } else {
                    throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
                }

            } else {
                throw new ResponseException("Product not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Name product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProductCreateDTO createProduct(ProductCreateDTO productCreateDTO) {
        try {
            //Buscar por nombre y Id de usuario para que pueda haber publicaciones con el mismo nombre de producto
            //Pero de distinto vendedor
            Optional<Product> productFound = productRepository.findByName(productCreateDTO.getName());
            if(productFound.isPresent()) {
                throw new ResponseException("Product already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Long idUser = productCreateDTO.getUser().getIdUser();
                Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser , UserDetailDTO.class));
                if(userFound.isPresent()) {
                    Long idCategory = productCreateDTO.getCategory().getIdCategory();
                    Optional<Category> categoryFound = categoryRepository.findById(idCategory);
                    if (categoryFound.isPresent()) {
                        Product product = ProductMapper.mapper.productCreateDtoToProduct(productCreateDTO);
                        productRepository.save(product);

                        return productCreateDTO;
                    } else {
                        throw new ResponseException("Category does not exist", null, HttpStatus.NOT_FOUND);
                    }
                } else {
                    throw new ResponseException("User does not exist", null, HttpStatus.NOT_FOUND);
                }
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductDetailDTO updateProduct(Long idProduct, ProductDetailDTO productDetailDTO) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if(productFound.isPresent()) {
                Long idUser = productDetailDTO.getUser().getIdUser();
                Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser , UserDetailDTO.class));
                if(userFound.isPresent()) {
                    Long idCategory = productDetailDTO.getCategory().getIdCategory();
                    Optional<Category> categoryFound = categoryRepository.findById(idCategory);
                    if (categoryFound.isPresent()) {
                        Product productUpdate = productFound.get();
                        productUpdate.setName(productDetailDTO.getName());
                        productUpdate.setPrice(productDetailDTO.getPrice());
                        productUpdate.setStock(productDetailDTO.getStock());
                        productUpdate.setDescription(productDetailDTO.getDescription());
                        productUpdate.setWeeklyOffer(productDetailDTO.isWeeklyOffer());
                        productRepository.save(productUpdate);

                        ProductDetailDTO productDetailDtoUpdate = ProductMapper.mapper.productToProductDetailDto(productUpdate);
                        return productDetailDtoUpdate;
                    } else {
                        throw new ResponseException("Category does not exist", null, HttpStatus.NOT_FOUND);
                    }
                } else {
                    throw new ResponseException("User does not exist", null, HttpStatus.NOT_FOUND);
                }

            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductDTO insertProductAttribute(Long idProduct, Long idAttribute) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if (productFound.isPresent()) {
                Optional<ProductAttribute> productAttributeFound = productAttributeRepository.findById(idAttribute);
                if(productAttributeFound.isPresent()) {
                    Product product = productFound.get();
                    ProductAttribute productAttribute = productAttributeFound.get();
                    product.getProductAttributes().add(productAttribute);
                    productRepository.save(product);

                    ProductDTO productDTO = ProductMapper.mapper.productToProductDto(product);
                    return productDTO;
                } else {
                    throw new ResponseException("That product attribute does not exist", null, HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Update product", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ProductDTO insertPaymentPlan(Long idProduct, Long idPayment) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if(productFound.isPresent()) {
                Optional<PaymentPlanDTO> paymentPlanFound = Optional.ofNullable(restTemplate.getForObject("http://transaction-service/payment-plan/" + idPayment, PaymentPlanDTO.class));
                if (paymentPlanFound.isPresent()) {
                    Product product = productFound.get();
                    PaymentPlanDTO paymentPlanDTO = paymentPlanFound.get();
                    PaymentPlan paymentPlan = PaymentMapper.mapper.paymentPlanDtoToPaymentPlan(paymentPlanDTO);
                    product.getPaymentPlans().add(paymentPlan);
                    productRepository.save(product);

                    ProductDTO productDTO = ProductMapper.mapper.productToProductDto(product);
                    return productDTO;
                } else {
                    throw new ResponseException("That Payment plan does not exist", null, HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Insert Payment plan", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ProductDTO removePaymentPlan(Long idProduct, Long idPayment) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if(productFound.isPresent()) {
                Optional<PaymentPlan> paymentPlanFound = Optional.ofNullable(restTemplate.getForObject("http://transaction-service/payment-plan/" + idPayment, PaymentPlan.class));
                if (paymentPlanFound.isPresent()) {
                    Product product = productFound.get();
                    PaymentPlan paymentPlan = paymentPlanFound.get();
                    product.getPaymentPlans().remove(paymentPlan);
                    productRepository.save(product);

                    ProductDTO productDTO = ProductMapper.mapper.productToProductDto(product);
                    return productDTO;
                } else {
                    throw new ResponseException("That Payment plan does not exist", null, HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Insert Payment plan", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ProductDTO deleteProduct(Long idProduct) {
        try {
            Optional<Product> productFound = productRepository.findById(idProduct);
            if(productFound.isPresent()) {
                Product productDelete = productFound.get();
                ProductDTO productDTO = ProductMapper.mapper.productToProductDto(productDelete);
                productRepository.delete(productDelete);

                return productDTO;
            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
