package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentMapper;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.product_service.model.paymentPlant.PaymentPlanDTO;
import com.mercado_liebre.product_service.model.product.*;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.user.User;
import com.mercado_liebre.product_service.model.user.UserDetailDTO;
import com.mercado_liebre.product_service.model.user.UserMapper;
import com.mercado_liebre.product_service.repository.CategoryFamilyRepository;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import com.mercado_liebre.product_service.repository.ProductAttributeRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryFamilyServiceImpl categoryFamilyService;
    @Autowired
    private CategoryFamilyRepository categoryFamilyRepository;
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
    public List<ProductDTO> getAllByCategoryName(String categoryName) {
        try {
            List<Product> products = productRepository.findByCategoryName(categoryName);
            List<ProductDTO> productDTOS = products.stream().map(
                    product -> ProductMapper.mapper.productToProductDto(product)).collect(Collectors.toList());

            return productDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail get all by category name", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
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
            System.out.println(e.getMessage());
            throw new ResponseException("Get By Name product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<Product> getByNameEntity(String name) {
        try {
            Optional<Product> productFound = productRepository.findByName(name);
            if(productFound.isPresent()) {

                return productFound;
            } else {
                throw new ResponseException("Product not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseException("Get By Name dto product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
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
    public List<ProductDTO> getProductsInOffer() {
        try {
            List<Product> products = productRepository.findProductsInOffer();
            List<ProductDTO> productDTOS = products.stream().map(
                    product -> ProductMapper.mapper.productToProductDto(product)).collect(Collectors.toList());

            return productDTOS;
        } catch (Exception e) {
            throw new ResponseException("Get products in weekly offer product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDTO> getProductsInWeeklyOffer() {
        try {
            List<Product> products = productRepository.findProductsByWeeklyOffer();
            List<ProductDTO> productDTOS = products.stream().map(
                    product -> ProductMapper.mapper.productToProductDto(product)).collect(Collectors.toList());
            return productDTOS;
        } catch (Exception e) {
            throw new ResponseException("Get products in weekly offer product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDetailDTO> getProductsByIdCategoryFamily(Long idCategoryFamily) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
            if (categoryFamilyFound.isPresent()) {
                List<Product> products = productRepository.findByIdCategoryFamily(idCategoryFamily);
                List<ProductDetailDTO> productDetailDTOS = products.stream().map(
                        product -> ProductMapper.mapper.productToProductDetailDto(product)).collect(Collectors.toList());

                return productDetailDTOS;
            } else {
                throw new ResponseException("Category family not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By id category family product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDTO> getProductsByTypeCategoryFamily(String categoryFamilyType) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findByType(categoryFamilyType);
            if (categoryFamilyFound.isPresent()) {
                List<Product> products = productRepository.findByTypeCategoryFamily(categoryFamilyType);
                List<ProductDTO> productDTOS = products.stream().map(
                        product -> ProductMapper.mapper.productToProductDto(product)).collect(Collectors.toList());

                return productDTOS;
            } else {
                throw new ResponseException("Category family not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By name category family product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDetailDTO> getLatestProductsInHistoryByIdUser(Long idUser) {
        try {
            Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser , UserDetailDTO.class));
            if(userFound.isPresent()) {
                User user = UserMapper.mapper.userDetailDtoToUser(userFound.get());
                if(!user.getProducts().isEmpty()) {
                   Product latestProductInUserHistory = user.getProducts().get(user.getProducts().size() - 1);
                   CategoryFamily latestCategoryFamilyInUserHistory = latestProductInUserHistory.getCategory().getCategoryFamily();

                   return this.getProductsByIdCategoryFamily(latestCategoryFamilyInUserHistory.getIdType());
                }

                return new ArrayList<>();
            } else {
                throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By id user product", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private Product getLatestProductInHistoryUser(Long idUser) {
        try {
            Optional<UserDetailDTO> userFound = Optional.ofNullable(restTemplate.getForObject("http://user-service/user/" + idUser , UserDetailDTO.class));
            if(userFound.isPresent()) {
                User user = UserMapper.mapper.userDetailDtoToUser(userFound.get());
                if(!user.getProducts().isEmpty()) {
                    Product latestProductInUserHistory = user.getProducts().get(user.getProducts().size() - 1);
                    return latestProductInUserHistory;
                }

            } else {
                throw new ResponseException("User not found", null, HttpStatus.NOT_FOUND);
            }
            return null;
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get latest user product in history", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDetailDTO> getProductsByLatestCategoryInUserHistory(Long idUser) {
        try {
            Product latestProduct = this.getLatestProductInHistoryUser(idUser);
            if(latestProduct != null) {
                List<Product> productsByLatestCategory = productRepository.findByLatestCategoryInUserHistory(latestProduct.getCategory().getName());
                List<ProductDetailDTO> productDetailDTOS = productsByLatestCategory.stream().map(
                        product -> ProductMapper.mapper.productToProductDetailDto(product)).collect(Collectors.toList());
                return productDetailDTOS;
            } else {
                return new ArrayList<>();
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get latest user product in history", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<ProductDetailDTO> getProductsInShoppingCart(Long idCart) {
        try {
            Long[] idProducts = Objects.requireNonNull(restTemplate.getForObject("http://transaction-service/shopping-cart/by-product/" + idCart, Long[].class));
            List<ProductDetailDTO> productDetailDTOS = new ArrayList<>();
            for(Long idProduct : idProducts) {
                Optional<Product> product = productRepository.findById(idProduct);
                ProductDetailDTO productDetailDTO = ProductMapper.mapper.productToProductDetailDto(product.get());
                productDetailDTOS.add(productDetailDTO);
            }

            return productDetailDTOS;
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get products user in shopping cart", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
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
