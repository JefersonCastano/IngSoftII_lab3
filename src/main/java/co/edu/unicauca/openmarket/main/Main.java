
package co.edu.unicauca.openmarket.main;

import co.edu.unicauca.openmarket.domain.service.Service;
import co.edu.unicauca.openmarket.presentation.GUIMenu;

/**
 *
 * @author Libardo Pantoja
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Service service = new Service();
        
        GUIMenu instance = new GUIMenu(service);
        
        instance.setVisible(true);
    }
    
}
