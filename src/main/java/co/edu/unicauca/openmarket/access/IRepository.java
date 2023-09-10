package co.edu.unicauca.openmarket.access;

import java.sql.Connection;

/**
 * Interfaz para un repositorio de base de datos
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public interface IRepository{
    
    void initDatabase();

    void connect();

    void disconnect();
    
    Connection getConection();
}
