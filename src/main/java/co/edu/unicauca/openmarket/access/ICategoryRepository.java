package co.edu.unicauca.openmarket.access;

import co.edu.unicauca.openmarket.domain.Category;
import java.util.List;

/**
 * Interfaz para un repositorio de categoria
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public interface ICategoryRepository {
    boolean save(Category newCategory);
    
    boolean delete(Long id);

    boolean edit(Long id, Category category);

    List<Category> findAll();

    Category findById(Long id);

    List<Category> findByName(String name);

        
}
