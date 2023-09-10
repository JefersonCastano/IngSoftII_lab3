package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Category;
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
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */

public class CategoryRepository implements ICategoryRepository {

    private IRepository repo;

    public CategoryRepository(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean save(Category newCategory) {
        
        try {
            //Validate product
            if (newCategory == null || newCategory.getName().isEmpty()) {
                return false;
            }
            repo.connect();
            String sql = "INSERT INTO category ( name ) "
                    + "VALUES ( ? );";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, newCategory.getName());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally{
            repo.disconnect();
        }    
    }

    @Override
    public List<Category> findAll() {
        
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM category;";
            repo.connect();

            Statement stmt = repo.getConection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Category newCategory = new Category();
                newCategory.setCategoryId(rs.getLong("categoryId"));
                newCategory.setName(rs.getString("name"));
                categories.add(newCategory);
            }
            rs.close();
            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return categories;
        }finally{
            repo.disconnect();
        }
    }

    @Override
    public boolean edit(Long id, Category category) {
        try {
            //Validate product
            if (id <= 0 || category == null) {
                return false;
            }
            repo.connect();
            String sql = "UPDATE  category "
                    + "SET name=? "
                    + "WHERE categoryId = ?;";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, category.getName());
            pstmt.setLong(2, id);
            return pstmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "DELETE FROM category "
                    + "WHERE categoryId = ?;";

            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            repo.disconnect();
        }
    }

    @Override
    public Category findById(Long id) {
        try {
            
            String sql = "SELECT * FROM category  "
                    + "WHERE categoryId = ?;";

            repo.connect();
            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet res = pstmt.executeQuery();
            
            if (res.next()) {
                Category category = new Category();
                category.setCategoryId(res.getLong("categoryId"));
                category.setName(res.getString("name"));
                res.close();
                return category;
            } else {
                res.close();
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            repo.disconnect();
        }
    }
    
    @Override
    public List<Category> findByName(String name){
        List<Category> categories = new ArrayList<>();
         try {
             
            String sql = "SELECT * FROM category  "
                    + "WHERE upper(name) = upper(?);";

            repo.connect();
            PreparedStatement pstmt = repo.getConection().prepareStatement(sql);
            pstmt.setString(1, name);
            
            ResultSet res = pstmt.executeQuery();
            
            while (res.next()) {
                Category newCategory = new Category();
                newCategory.setCategoryId(res.getLong("categoryId"));
                newCategory.setName(res.getString("name"));

                categories.add(newCategory);
            }
            res.close();
            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryRepository.class.getName()).log(Level.SEVERE, null, ex);
            return categories;
        }finally{
            repo.disconnect();
        }
    }
}
