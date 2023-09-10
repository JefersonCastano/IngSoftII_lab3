package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.domain.service.TestUtilities.TestUtilities;
import co.edu.unicauca.openmarket.access.CategoryRepository;
import co.edu.unicauca.openmarket.access.Factory;
import co.edu.unicauca.openmarket.access.ProductRepository;
import co.edu.unicauca.openmarket.domain.Category;
import co.edu.unicauca.openmarket.domain.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la gestion de productos
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public class ProductServiceTest {
    
    static ProductService productService;
    static CategoryService categoryService;
    static TestUtilities help;
    Category category = new Category(1l, "Tecnologia");
    
    public ProductServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        productService = new ProductService((ProductRepository) Factory.getInstance().getRepository("PRODUCT"));  
        categoryService = new CategoryService((CategoryRepository) Factory.getInstance().getRepository("CATEGORY")); 
        help = new TestUtilities();
        help.resetDatabase();
        categoryService.saveCategory("Tecnologia");
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {  
        categoryService.saveCategory("Tecnologia");
    }
    
    @AfterEach
    public void tearDown() {
        help.resetDatabase();
    }

    /**
     * Test of saveProduct method, of class ProductService.
     */
    @Test
    public void testSaveProductSuccesfull() {
        String name = "Celular";
        String description = "Motorola";
        boolean expResult = true;
        boolean result = productService.saveProduct(name, description, category);
        assertEquals(expResult, result); 
    }
    /**
     * Test of saveProduct method, of class ProductService. Empty name.
     */
    @Test
    public void testSaveProductEmptyName() {
        String name = "";
        String description = "Motorola";
        boolean expResult = false;
        boolean result = productService.saveProduct(name, description, category);
        assertEquals(expResult, result); 
    }
    /**
     * Test of saveProduct method, of class ProductService. Empty Description
     */
    @Test
    public void testSaveProductEmptyDescription() {
        String name = "Celular";
        String description = "";
        boolean expResult = true;
        boolean result = productService.saveProduct(name, description, category);
        assertEquals(expResult, result); 
    }
    
    /**
     * Test of deleteProduct method, of class ProductService.
     */
    @Test
    public void testDeleteProductSuccesfull() {
        productService.saveProduct("Delete test", "", category);
        Long id = 1l;
        boolean expResult = true;
        boolean result = productService.deleteProduct(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of deleteProduct method, of class ProductService. Negative Id
     */
    @Test
    public void testDeleteProductNegativeId() {
        Long id = -1l;
        boolean expResult = false;
        boolean result = productService.deleteProduct(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of deleteProduct method, of class ProductService. Product not found
     */
    @Test
    public void testDeleteProductNotFound() {
        Long id = 10l;
        boolean expResult = false;
        boolean result = productService.deleteProduct(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of editProduct method, of class ProductService.
     */
    @Test
    public void testEditProductSuccesfull() {
        productService.saveProduct("Edit test", "", category);
        Long productId = 1l;
        Product product = new Product();
        product.setProductId(productId);
        product.setName("new name");
        product.setDescription("new description");
        product.setCategory(category);
        boolean expResult = true;
        boolean result = productService.editProduct(productId, product);       
        assertEquals(expResult, result);  
    }
    
    /**
     * Test of editProduct method, of class ProductService. Empty name.
     */
    @Test
    public void testEditProductEmptyName() {
        productService.saveProduct("Edit test", "", category);
        Long productId = 1l;
        Product product = new Product();
        product.setProductId(productId);
        product.setName("");
        product.setDescription("new description");
        product.setCategory(category);
        boolean expResult = false;
        boolean result = productService.editProduct(productId, product);       
        assertEquals(expResult, result);  
    }
    /**
     * Test of editProduct method, of class ProductService. Product null.
     */
    @Test
    public void testEditProductNull() {
        productService.saveProduct("Edit test", "", category);
        Long productId = 1l;
        Product product = null;
        boolean expResult = false;
        boolean result = productService.editProduct(productId, product);       
        assertEquals(expResult, result);  
    }
    
    /**
     * Test of editProduct method, of class ProductService. Not found.
     */
    @Test
    public void testEditProductNotFound() {
        productService.saveProduct("Edit test", "", category);
        Long productId = 10l;
        Product product = new Product();
        product.setProductId(productId);
        product.setName("new name");
        product.setDescription("new description");
        product.setCategory(category);
        boolean expResult = false;
        boolean result = productService.editProduct(productId, product);       
        assertEquals(expResult, result);  
    }
    /**
     * Test of editProduct method, of class ProductService. Negative Id.
     */
    @Test
    public void testEditProductNegativeId() {
        productService.saveProduct("Edit test", "", category);
        Long productId = -1l;
        Product product = new Product();
        product.setProductId(productId);
        product.setName("new name");
        product.setDescription("new description");
        product.setCategory(category);
        boolean expResult = false;
        boolean result = productService.editProduct(productId, product);       
        assertEquals(expResult, result);  
    }

    /**
     * Test of findAllProducts method, of class ProductService. 
     */
    @Test
    public void testFindAllProductsSuccesfull() {
        productService.saveProduct("Celular", "Motorola", category);
        productService.saveProduct("Nevera", "", category);
        productService.saveProduct("TV", "55'", category);
        
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(1l);
        product.setName("Celular");
        product.setDescription( "Motorola");
        product.setCategory(category);
        products.add(product);
        product = new Product();
        product.setProductId(2l);
        product.setName("Nevera");
        product.setDescription("");
        product.setCategory(category);
        products.add(product);
        product = new Product();
        product.setProductId(3l);
        product.setName("TV");
        product.setDescription("55'");
        product.setCategory(category);
        products.add(product);

        List<Product> expArray = products;
        List<Product> actualArray = productService.findAllProducts();
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
        
    }
    /**
     * Test of findAllProducts method, of class ProductService. Empty list
     */
    @Test
    public void testFindAllProductsEmpty() {
        List<Product> products = new ArrayList<>();

        List<Product> expArray = products;
        List<Product> actualArray = productService.findAllProducts();
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }

    /**
     * Test of findProductById method, of class ProductService.
     */
    @Test
    public void testFindProductByIdSuccesfull() {
        productService.saveProduct("FindById test", "", category);
        Long id = 1l;
        Product expProduct = new Product();
        expProduct.setProductId(id);
        expProduct.setName("FindById test");
        expProduct.setDescription("");
        expProduct.setCategory(category);
        
        Product actualProduct = productService.findProductById(id);
        boolean expResult = true;
        boolean result = help.compareProd(expProduct, expProduct);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of findProductById method, of class ProductService. Not found.
     */
    @Test
    public void testFindProductByIdNotFound() {
        Long id = 10l;
        Product expProduct = null;
        Product actualPorduct = productService.findProductById(id);
        assertEquals(expProduct, actualPorduct);
    }

    /**
     * Test of findProductByName method, of class ProductService. One Result
     */
    @Test
    public void testFindProductByNameOneResult() {

        productService.saveProduct("Celular", "Motorola", category);
        productService.saveProduct("Nevera", "", category);
        productService.saveProduct("TV", "55'", category);
        
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(1l);
        product.setName("Celular");
        product.setDescription( "Motorola");
        product.setCategory(category);
        products.add(product);

        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByName("Celular");
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    
    /**
     * Test of findProductByName method, of class ProductService. Multiple Result
     */
    @Test
    public void testFindProductByNameMultResult() {
        productService.saveProduct("Nevera", "Grande", category);
        productService.saveProduct("Nevera", "Pequeña", category);
        productService.saveProduct("TV", "55'", category);
        
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(1l);
        product.setName("Nevera");
        product.setDescription( "Grande");
        product.setCategory(category);
        products.add(product);
        product = new Product();
        product.setProductId(2l);
        product.setName("Nevera");
        product.setDescription( "Pequeña");
        product.setCategory(category);
        products.add(product);

        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByName("Nevera");
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    
    /**
     * Test of findProductByName method, of class ProductService. No Result
     */
    @Test
    public void testFindProductByNameNoResult() {
        productService.saveProduct("Celular", "Grande", category);
        productService.saveProduct("Nevera", "Pequeña", category);
        productService.saveProduct("TV", "55'", category);
        
        List<Product> products = new ArrayList<>();


        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByName("PC");
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }

    /**
     * Test of findProductByCategory method, of class ProductService. One Result
     */
    @Test
    public void testFindProductByCategoryOneResult() {
        categoryService.saveCategory("Alimentos");
        Category category2 = new Category(2l, "Alimentos");
        
        productService.saveProduct("Celular", "Motorola", category);
        productService.saveProduct("Pollo", "", category2);
        productService.saveProduct("Pescado", "", category2);
        
        Long categoryId = 1l;
        
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(1l);
        product.setName("Celular");
        product.setDescription( "Motorola");
        product.setCategory(category);
        products.add(product);

        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByCategory(categoryId);
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    /**
     * Test of findProductByCategory method, of class ProductService. Multiple Result
     */
    @Test
    public void testFindProductByCategoryMultResult() {
        categoryService.saveCategory("Alimentos");
        Category category2 = new Category(2l, "Alimentos");
        
        productService.saveProduct("Celular", "Motorola", category);
        productService.saveProduct("Pollo", "", category2);
        productService.saveProduct("Pescado", "", category2);
        
        Long categoryId = 2l;
        
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(2l);
        product.setName("Pollo");
        product.setDescription( "");
        product.setCategory(category2);
        products.add(product);
        product = new Product();
        product.setProductId(3l);
        product.setName("Pescado");
        product.setDescription( "");
        product.setCategory(category2);
        products.add(product);

        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByCategory(categoryId);
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    /**
     * Test of findProductByCategory method, of class ProductService. No Result
     */
    @Test
    public void testFindProductByCategoryNoResult() {
        categoryService.saveCategory("Alimentos");
        Category category2 = new Category(2l, "Alimentos");
        
        productService.saveProduct("Celular", "Motorola", category);
        productService.saveProduct("Pollo", "", category2);
        productService.saveProduct("Pescado", "", category2);
        
        Long categoryId = 10l;
        
        List<Product> products = new ArrayList <>();

        List<Product> expArray = products;
        List<Product> actualArray = productService.findProductByCategory(categoryId);
        
        boolean expResult = true;
        boolean result = help.areProductsEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
}
