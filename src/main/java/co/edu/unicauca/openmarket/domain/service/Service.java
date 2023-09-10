package co.edu.unicauca.openmarket.domain.service;

import co.edu.unicauca.openmarket.access.CategoryRepository;
import co.edu.unicauca.openmarket.access.Factory;
import co.edu.unicauca.openmarket.access.ProductRepository;

/**
 * Servicios para la gestion de productos y categorias
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */

public class Service {
    
    public CategoryService category;
    public ProductService product;
    
    public Service(){
        product = new ProductService ((ProductRepository) Factory.getInstance().getRepository("PRODUCT"));
        category = new CategoryService ((CategoryRepository) Factory.getInstance().getRepository("CATEGORY"));
    }
}
