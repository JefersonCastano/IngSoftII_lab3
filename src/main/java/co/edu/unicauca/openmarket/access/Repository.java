package co.edu.unicauca.openmarket.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Es una implementación concreta que tiene como implementación del
 * contrato IRepository. 
 *
 * @author Jeferson Castano Ossa <jcastanoossa@unicauca.edu.co>
 * @author David Santiago Giron Munoz <davidgiron@unicauca.edu.co>
 */
public class Repository implements IRepository{
    
    private Connection conn;
    
    public Repository(){
        initDatabase();
    }
    
    @Override
    public void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS category (\n"
                + "	categoryId integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL\n"
                + ");\n";
        String sql2 = "CREATE TABLE IF NOT EXISTS products (\n"
                + "	productId integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	description text NULL,\n"
                + "	categoryId integer NOT NULL,\n"
                + "     FOREIGN KEY (categoryId) REFERENCES category(categoryId)"
                + ");\n";

        try {
            connect();
            Statement stmt = getConection().createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            disconnect();
        }
    }

    @Override
    public void connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:./myDatabase.db"; //Para Linux/Mac
        //String url = "jdbc:sqlite:C:/sqlite/db/myDatabase.db"; //Para Windows
        String url = "jdbc:sqlite:myDatabase.db";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void disconnect() {
        try {
            if (getConection() != null) {
                getConection().close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * @return the conn
     */
    @Override
    public Connection getConection() {
        return conn;
    }
}

