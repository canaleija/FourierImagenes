/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.awt.Dimension;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Roberto Cruz Leija
 */
public class Filtros {
    
    public static Mat crearFiltroGaussiano(Size tam, int x){
        
        Mat filter = new Mat(tam,CvType.CV_64FC2);
        Point center = new Point(tam.width/2, tam.height/2);
        //double data[] = new double[(int)filter.total()*filter.channels()];
        for (int j=0; j < filter.rows();j++)
            for (int i=0; i < filter.cols();i++){
               filter.put(j, i, coeficienteGuassiano(j-center.y,i-center.x,x));
          
            }
        
    return filter;
    }

    private static double coeficienteGuassiano(double u, double v, int d0) {
        double d = distanciaPixeles(u, v);
        return 1.0 - (Math.exp((-d*d) / (2*d0*d0)));
  
    }

    private static double distanciaPixeles(double u, double v) {
       return Math.sqrt(u*u + v*v);

    }
    
    public static Image matToImage(Mat imagen) throws IOException{
       
        // buffer 
        MatOfByte buffer = new MatOfByte();
        
        Imgcodecs.imencode(".png", imagen, buffer);
        
        // genera la imagen 
        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.toArray());
        Image i = ImageIO.read(bi);
        
        return i;
        
    }
    public static Mat crearFiltroIdealPasaAltas (Size tam,int radio){
      // instanciar el filtr 
      Mat filtro = new Mat(tam, CvType.CV_32F);
      
      int x,y;
      double r;
      // recorrer el mat para modificar los valores
      for (int j=0; j < filtro.rows();j++)
          for (int i=0; i < filtro.cols();i++){
                 x = -1 * ((int)tam.width/2) + i;
                 y = ((int)tam.height/2) - j;
                 r = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
                 if (r <=radio){
                   filtro.put(j, i, 255);
                 }else {
                   filtro.put(j, i,0);
                 }
          }
      return filtro;
    }
    
    
  
}


