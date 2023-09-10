package co.edu.unicauca.openmarket.domain.service.TestUtilities;

import co.edu.unicauca.openmarket.access.Factory;
import co.edu.unicauca.openmarket.access.IRepository;
import co.edu.unicauca.openmarket.access.Repository;
import co.edu.unicauca.openmarket.domain.Category;
import co.edu.unicauca.openmarket.domain.Product;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacion de utilidades para la ejecucion de pruebas para el modelo de negocio
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public class TestUtilities {
    private IRepository repo;

    public TestUtilities(){
        repo = new Repository();
    }
    public void resetDatabase() {
        // SQL statement for creating a new table
        String sql = "DROP TABLE PRODUCTS;";
        String sql2 = "DROP TABLE CATEGORY;";
        String sql3 = "CREATE TABLE IF NOT EXISTS category (\n"
                + "	categoryId integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL\n"
                + ");\n";
        String sql4 = "CREATE TABLE IF NOT EXISTS products (\n"
                + "	productId integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	description text NULL,\n"
                + "	categoryId integer NOT NULL,\n"
                + "     FOREIGN KEY (categoryId) REFERENCES category(categoryId)"
                + ");\n";
        
        try {
            repo.connect();
            Statement stmt = repo.getConection().createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
        } catch (SQLException ex) {
            Logger.getLogger(TestUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            repo.disconnect();
        }
    }
    
    public boolean areProductsEquals(List<Product> prod1, List<Product> prod2){
        int i = 0;
        if(prod1.size() != prod2.size()) return false;
        for(Product prod : prod1){
            if(!compareProd(prod, prod2.get(i))) return false;
            i++;
        }
        return true;
    }
    
    public boolean compareProd(Product prod1, Product prod2){
        return Objects.equals(prod1.getProductId(), prod2.getProductId()) 
                && prod1.getName().equals(prod2.getName()) 
                && prod1.getDescription().equals(prod2.getDescription()) 
                && compareCat(prod1.getCategory(), prod2.getCategory());
    }
    
    public boolean areCategoriesEquals(List<Category> cat1, List<Category> cat2){
        int i = 0;
        if(cat1.size() != cat2.size()) return false;
        for(Category cat : cat1){
            if(!compareCat(cat, cat2.get(i))) return false;
            i++;
        }
        return true;
    }
    
    public boolean compareCat(Category cat1, Category cat2){
        return Objects.equals(cat1.getCategoryId(), cat2.getCategoryId()) 
                && cat1.getName().equals(cat2.getName());
    }
}
