package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Category;
import co.edu.unicauca.openmarket.domain.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es una implementación que tiene libertad de hacer una implementación del
 * contrato. Lo puede hacer con Sqlite, postgres, mysql, u otra tecnología
 *
 * @author Libardo, Julio
 */
public class ProductRepository implements IProductRepository {

    private IRepository repo;

    public ProductRepository(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean save(Product newProduct) {

        try {
            
            //Validate product
            if (newProduct == null || newProduct.getName().isEmpty()) {
                return false;
            }
            repo.connect();
            String sql = "INSERT INTO products ( name, description, categoryId ) "
                    + "VALUES ( ?, ?, ? );";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, newProduct.getName());
            pstmt.setString(2, newProduct.getDescription());
            pstmt.setLong(3, newProduct.getCategory().getCategoryId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally{
            repo.disconnect();
        } 
    }

    @Override
    public List<Product> findAll() {
        
        List<Product> products = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM products;";
            repo.connect();
            Statement stmt = repo.getConection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                products.add(generateProduct(rs));
            }
            rs.close();
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return products;
        } finally{
            repo.disconnect();
        } 
    }

    @Override
    public boolean edit(Long id, Product product) {
        try {
            //Validate product
            if (id <= 0 || product == null) {
                return false;
            }
            repo.connect();
            String sql = "UPDATE  products "
                    + "SET name=?, description=?, categoryId=? "
                    + "WHERE productId = ?;";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setLong(3, product.getCategory().getCategoryId());
            pstmt.setLong(4, id);
            
            return pstmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
             return false;
        }finally{
            repo.disconnect();
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            //Validate product
            if (id <= 0) {
                return false;
            }
            repo.connect();
            String sql = "DELETE FROM products "
                    + "WHERE productId = ?;";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setLong(1, id);
            
            return pstmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            repo.disconnect();
        }
    }

    @Override
    public Product findById(Long id) {
        try {

            String sql = "SELECT * FROM products "
                    + "WHERE productId = ?;";
            repo.connect();
            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                Product p = generateProduct(res);
                res.close();
                return p;
            } else {
                res.close();
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            repo.disconnect();
        } 
    }
    
    @Override
    public List<Product> findByName(String name){
        List<Product> products = new ArrayList<>();
         try {
            String sql = "SELECT * FROM products  "
                    + "WHERE upper(name) = upper(?);";

            repo.connect();
            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, name);

            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                products.add(generateProduct(res));
            }
            res.close();
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return products;
        }finally{
            repo.disconnect();
        }
    }
    @Override
    public List<Product> findByCategory(Long id) {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM products inner join category "
                    + "on products.categoryId = category.categoryId "
                    + "WHERE category.categoryId = ?;";

            repo.connect();
            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                products.add(generateProduct(res));
            }
            res.close();
            return products;
   
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
            return products;
        } finally{
            repo.disconnect();
        } 
    }
    
        private Product generateProduct (ResultSet rs) throws SQLException{
        
        CategoryRepository cr = new CategoryRepository(repo);
        
        Product prod = new Product();
        prod.setProductId(rs.getLong("productId"));
        prod.setName(rs.getString("name"));
        prod.setDescription(rs.getString("description"));
        prod.setCategory((Category) cr.findById(rs.getLong("categoryId")));
        return prod;
    }
}
