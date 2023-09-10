package co.edu.unicauca.openmarket.access;

/**
 * Fabrica que se encarga de instanciar ProductRepository o cualquier otro que
 * se cree en el futuro.
 *
 * @author Libardo, Julio
 */
public class Factory {

    private static Factory instance;
    private static IRepository repository;
    
    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
            repository = new Repository();
        }
        return instance;
    }

    /**
     * Método que crea una instancia de un repositorio
     *
     * @param type cadena que indica qué tipo de repositorio instanciar
     * @return Objeto del repositorio concreto que se desea instanciar
     */
    public Object getRepository(String type) {

        Object result = null;
        
        switch (type) {
            case "PRODUCT":
                result = new ProductRepository(repository);
                break;
            case "CATEGORY":
                result = new CategoryRepository(repository);
                break;
        }
        return result;
    }
}
