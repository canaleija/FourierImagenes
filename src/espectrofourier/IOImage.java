/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espectrofourier;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Roberto Cruz Leija
 */
public class IOImage {
    
    public static String abrirImagen(){
          
            // definir los fltros de los tipos de imagenes
            FileNameExtensionFilter filtro =
                    new FileNameExtensionFilter("Imagenes","png","jpg","bmp");
            // declarar e inicializar la venta de seleccion
            JFileChooser seleccion = new JFileChooser();
            seleccion.addChoosableFileFilter(filtro);
            // especificamos que solo puede abrir archivos
            seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            // ejecutar el selector de archivos
            int resutlado = seleccion.showOpenDialog(null);
            // si el evento fue cancelar
            if (resutlado== JFileChooser.CANCEL_OPTION){
                return null;
            }
            return  seleccion.getSelectedFile().getPath();
     
           }
   
    
}
