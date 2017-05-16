/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espectrofourier;

import filtros.Filtros;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Roberto Cruz Leija
 */
public class Fourier {
    
    private String ruta;
    private Mat imagenOriginal,complexImage,magnitude;
    private List<Mat> planes;

    public Fourier(String ruta) {
        this.ruta = ruta;
        this.complexImage = new Mat();
        
    }
    public void generarImagen(){
      // leer la imagen desde l ruta
      this.imagenOriginal = Imgcodecs.imread(ruta,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
      System.out.println();
        
    }
    
    //
    
    public Image transformImage() throws IOException{
       
        // optimizar la dimension de la imagen
        Mat padded = optimizedImageDim(this.imagenOriginal);
        padded.convertTo(padded, CvType.CV_32FC1);
        // preparar los planos para calular la dft
        this.planes = new ArrayList<>();
        this.planes.add(padded);
        this.planes.add(Mat.zeros(padded.size(), CvType.CV_32FC1));
        // prepar o mezclar para calcular el dft
        Core.merge(planes,complexImage);
        // dft 
        Core.dft(complexImage, complexImage);
        // optmización para obtener las magnitudes
        this.magnitude = createOptimzeMagnitude(complexImage);
        
      return Filtros.matToImage(magnitude);
    }

    /**
     * @return the imagenOriginal
     */
    public Mat getImagenOriginal() {
        return imagenOriginal;
    }

    private Mat optimizedImageDim(Mat imagenOriginal) {
        Mat padded = new Mat();
        
        int addPixelRows = Core.getOptimalDFTSize(imagenOriginal.rows());
        int addPixelCols = Core.getOptimalDFTSize(imagenOriginal.cols());
        // aplicar el numero de columnas y renglones
        Core.copyMakeBorder(imagenOriginal, padded,0, addPixelRows - imagenOriginal.rows(), 0, addPixelCols-imagenOriginal.cols(),Core.BORDER_CONSTANT,Scalar.all(0));
        
        return padded;
    }

    private Mat createOptimzeMagnitude(Mat complexImage) {
        
        List<Mat> planes = new ArrayList<>();
        Mat mag = new Mat();
        // generar los dos planos en base a la matriz de complejos
        Core.split(complexImage, planes);
        // calcular las magnitudes 
        Core.magnitude(planes.get(0),planes.get(1), mag);
        // optmización de la escala logaritmica 
        Core.add(Mat.ones(mag.size(), CvType.CV_32F),mag,mag);
        Core.log(mag, mag);
        // todo: acomodar los cuadrantes
        Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX);
        
        
        return mag;
    }
    
    public Image antitransformImage() throws IOException
	{
		Core.idft(this.complexImage, this.complexImage);
		
		Mat restoredImage = new Mat();
		Core.split(this.complexImage, this.planes);
		Core.normalize(this.planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
		
		
		return Filtros.matToImage(restoredImage);
		
	}

    public Image applyFilter(Mat filter) throws IOException{
        
        Mat dest = new Mat(complexImage.size(), CvType.CV_32F);
        List<Mat> planos = new ArrayList<>();
        planos.add(filter);
        planos.add(complexImage);
        Core.merge(planos, dest);
        
        return Filtros.matToImage(dest);
    }
     
    
}
