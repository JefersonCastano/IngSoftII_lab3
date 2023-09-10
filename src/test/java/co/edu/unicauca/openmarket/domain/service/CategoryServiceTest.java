package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.domain.service.TestUtilities.TestUtilities;
import co.edu.unicauca.openmarket.access.CategoryRepository;
import co.edu.unicauca.openmarket.access.Factory;
import co.edu.unicauca.openmarket.domain.Category;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la gestion de categorias
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public class CategoryServiceTest {
    
    static CategoryService categoryService;
    static TestUtilities help;
    
    public CategoryServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() { 
        categoryService = new CategoryService((CategoryRepository) Factory.getInstance().getRepository("CATEGORY"));  
        help = new TestUtilities();
        help.resetDatabase();
    }
    
    @AfterAll
    public static void tearDownClass() { 
    }
    
    @BeforeEach
    public void setUp() {    
    }
    
    @AfterEach
    public void tearDown() { 
        help.resetDatabase();
    }
    
    /**
     * Test of saveCategory method, of class CategoryService.
     */
    @Test
    public void testSaveCategorySuccesfull() {
        String name = "Escolar";
        boolean expResult = true;
        boolean result = categoryService.saveCategory(name);
        assertEquals(expResult, result);    
    }
    
    /**
     * Test of saveCategory method, of class CategoryService. Empty name.
     */
    @Test
    public void testSaveCategoryEmptyName() {
        String name = "";
        boolean expResult = true;
        boolean result = categoryService.saveCategory(name);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of deleteCategory method, of class CategoryService.
     */
    @Test
    public void testDeleteCategorySuccesfull() {
        categoryService.saveCategory("Delete test");
        Long id = 1l;
        boolean expResult = true;
        boolean result = categoryService.deleteCategory(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of deleteCategory method, of class CategoryService. Negative number.
     */
    @Test
    public void testDeleteCategoryNegativeNum() {
        Long id = -1l;
        boolean expResult = true;
        boolean result = categoryService.deleteCategory(id);
        assertNotEquals(expResult, result);
    }
    
    /**
     * Test of deleteCategory method, of class CategoryService. non-existing Category.
     */
    @Test
    public void testDeleteCategoryNotFound() {
        Long id = 10l;
        boolean expResult = true;
        boolean result = categoryService.deleteCategory(id);
        assertNotEquals(expResult, result);
    }
    

    /**
     * Test of editCategory method, of class CategoryService.
     */
    @Test
    public void testEditCategorySuccesfull() {
        categoryService.saveCategory("Edit test");
        Long categoryId = 1l;
        Category category = new Category(categoryId, "Edit test");        
        boolean expResult = true;
        boolean result = categoryService.editCategory(categoryId, category);       
        assertEquals(expResult, result);   
    }
    
    /**
     * Test of editCategory method, of class CategoryService. Category is null;
     */
    @Test
    public void testEditCategoryNull() {
        categoryService.saveCategory("Edit test");
        Long categoryId = 1l;
        Category category = null;        
        boolean expResult = false;
        boolean result = categoryService.editCategory(categoryId, category);       
        assertEquals(expResult, result);   
    }
    
    /**
     * Test of editCategory method, of class CategoryService. Category name is empty;
     */
    @Test
    public void testEditCategoryNameEmpty() {
        categoryService.saveCategory("Edit test");
        Long categoryId = 1l;
        Category category = new Category(categoryId, "");
        boolean expResult = false;
        boolean result = categoryService.editCategory(categoryId, category);       
        assertEquals(expResult, result);   
    }
    
     /**
     * Test of editCategory method, of class CategoryService. Category not found;
     */
    @Test
    public void testEditCategoryNotFound() {
        categoryService.saveCategory("Edit test");
        Long categoryId = 10l;
        Category category = new Category(categoryId, "Edit test");
        boolean expResult = false;
        boolean result = categoryService.editCategory(categoryId, category);       
        assertEquals(expResult, result);   
    }
    
     /**
     * Test of editCategory method, of class CategoryService. Negative Category Id;
     */
    @Test
    public void testEditCategoryNegativeNum() {
        categoryService.saveCategory("Edit test");
        Long categoryId = -1l;
        Category category = new Category(categoryId, "Edit test");
        boolean expResult = false;
        boolean result = categoryService.editCategory(categoryId, category);       
        assertEquals(expResult, result);   
    }

    /**
     * Test of findAllCategories method, of class CategoryService.
     */
    @Test
    public void testFindAllCategoriesSuccesfull() {
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat2");
        categoryService.saveCategory("cat3");
        
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "cat1"));
        categories.add(new Category(2l, "cat2"));
        categories.add(new Category(3l, "cat3"));

        List<Category> expArray = categories;
        List<Category> actualArray = categoryService.findAllCategories();
        
        boolean expResult = true;
        boolean result = help.areCategoriesEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    /**
     * Test of findAllCategories method, of class CategoryService. Empty List
     */
    @Test
    public void testFindAllCategoriesEmpty() {
        
        List<Category> categories = new ArrayList<>();

        List<Category> expArray = categories;
        List<Category> actualArray = categoryService.findAllCategories();
        
        boolean expResult = true;
        boolean result = help.areCategoriesEquals(expArray, actualArray);
        assertEquals(expResult, result); 
    }
    
    /**
     * Test of findCategoryById method, of class CategoryService.
     */
    @Test
    public void testFindCategoryByIdSuccesfull() {
        categoryService.saveCategory("FindById test");
        Long id = 1l;
        Category expCategory = new Category(1l, "FindById test");
        Category actualCategory = categoryService.findCategoryById(id);
        boolean expResult = true;
        boolean result = help.compareCat(expCategory, actualCategory);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of findCategoryById method, of class CategoryService. Not Found.
     */
    @Test
    public void testFindCategoryByIdNotFound() {
        Long id = 10l;
        Category expCategory = null;
        Category actualCategory = categoryService.findCategoryById(id);
        
        assertEquals(expCategory, actualCategory);
    }

    /**
     * Test of findCategoryByName method, of class CategoryService. One result. 
     */
    @Test
    public void testFindCategoryByNameOneResult() {
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat2");
        categoryService.saveCategory("cat3");
        
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(2l, "cat2"));

        List<Category> expArray = categories;
        List<Category> actualArray = categoryService.findCategoryByName("cat2");
        
        boolean expResult = true;
        boolean result = help.areCategoriesEquals(expArray, actualArray);
        assertEquals(expResult, result);
    }  
    
    /**
     * Test of findCategoryByName method, of class CategoryService. Multiple Result.
     */
    @Test
    public void testFindCategoryByNameMultResult() {
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat3");
        
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "cat1"));
        categories.add(new Category(2l, "cat1"));
        

        List<Category> expArray = categories;
        List<Category> actualArray = categoryService.findCategoryByName("cat1");
        
        boolean expResult = true;
        boolean result = help.areCategoriesEquals(expArray, actualArray);
        assertEquals(expResult, result);
    } 
    /**
     * Test of findCategoryByName method, of class CategoryService. No result.
     */
    @Test

    public void testFindCategoryByNameNoResult() {
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat1");
        categoryService.saveCategory("cat3");
        
        List<Category> categories = new ArrayList<>();
        

        List<Category> expArray = categories;
        List<Category> actualArray = categoryService.findCategoryByName("cat4");
        
        boolean expResult = true;
        boolean result = help.areCategoriesEquals(expArray, actualArray);
        assertEquals(expResult, result);
    }
}
