/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.mmaracic.opencvhough;

import hr.mmaracic.opencvdemo.Util;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author mmaracic
 */
public class OpenCVHoughDemo {

    public static void main(String[] args) {

        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String fileName = ClassLoader.getSystemClassLoader().getResource("pool_balls.jpg").getFile().substring(1);
        Mat img = Imgcodecs.imread(fileName);
        if (img.width() == 0 && img.height() == 0) {
            throw new IllegalArgumentException("Incorrect path, dimensions of the image are 0");
        }
        System.out.println("Image size: Width: "+img.cols()+" Height: "+img.rows());
        Image i1 = Util.toBufferedImage(img);
        Util.displayImage(i1, "Balls image");

        Mat gray = new Mat(img.rows(), img.cols(), CvType.CV_8UC1);
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);
        
        //Imgproc.medianBlur(gray, gray, 11);

        Image i2 = Util.toBufferedImage(gray);
        Util.displayImage(i2, "Balls gray image");

        //Default constructor is enough, data is actually allocated by 
        Mat circles = new Mat();
        //Tennis balls
        //Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, 10, 100, 30, 10, img.cols());
        //Pool balls
        Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, 100, 100, 30, 100, img.cols());
        System.out.println("Found circles: "+circles.cols());
        Image i3 = Util.toBufferedImage(img);
        Graphics g = i3.getGraphics();
        g.setColor(Color.red);
        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            //subtracttion due to display error or algorithm?
            int x = (int) Math.round(circle[0])-35;
            int y = (int) Math.round(circle[1])-35;
            int d = 2 * (int) Math.round(circle[2]);
            System.out.println("Circle " + i + " Center x: " + x + " Center y: " + y + " Diameter: " + d);
            g.drawOval(x, y, d, d);

        }
        Util.displayImage(i3, "Balls with hough circles");

    }

}
