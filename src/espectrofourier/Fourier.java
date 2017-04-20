/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espectrofourier;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import sun.audio.AudioDataStream;

/**
 *
 * @author Roberto Cruz Leija
 */
public class Fourier {
    
    private String ruta;
    private Mat imagenOriginal;

    public Fourier(String ruta) {
        this.ruta = ruta;
    }
    public void generarImagen(){
      // leer la imagen desde l ruta
      this.imagenOriginal = Imgcodecs.imread(ruta,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
      System.out.println();
        
    }
    public Image matToImage(Mat imagen) throws IOException{
       
        // buffer 
        MatOfByte buffer = new MatOfByte();
        
        Imgcodecs.imencode(".png", imagen, buffer);
        
        // genera la imagen 
        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.toArray());
        Image i = ImageIO.read(bi);
        
        return i;
        
    }
     
    
}
