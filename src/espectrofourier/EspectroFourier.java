/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espectrofourier;

import org.opencv.core.Core;

/**
 *
 * @author Roberto Cruz Leija
 */
public class EspectroFourier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        JFrameFourier frame = new JFrameFourier();
        frame.setVisible(true);
    }
    
}
