package com.cycloneboy.springcloud.common.utils.image;

/**
 * Create by  sl on 2019-08-27 23:56
 */

import static org.bytedeco.opencv.global.opencv_calib3d.Rodrigues;
import static org.bytedeco.opencv.global.opencv_core.CV_64FC1;
import static org.bytedeco.opencv.global.opencv_core.CV_8UC1;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_AA;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_RETR_LIST;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.opencv.global.opencv_imgproc.approxPolyDP;
import static org.bytedeco.opencv.global.opencv_imgproc.arcLength;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.drawContours;
import static org.bytedeco.opencv.global.opencv_imgproc.fillConvexPoly;
import static org.bytedeco.opencv.global.opencv_imgproc.findContours;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.global.opencv_imgproc.threshold;
import static org.bytedeco.opencv.global.opencv_imgproc.warpPerspective;

import java.io.File;
import java.net.URL;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

public class Demo {

  public static void main(String[] args) throws Exception {
    String classifierName = null;
    if (args.length > 0) {
      classifierName = args[0];
    } else {
      URL url = new URL(
          "https://raw.github.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_alt.xml");
      File file = Loader.cacheResource(url);
      classifierName = file.getAbsolutePath();
    }

    // We can "cast" Pointer objects by instantiating a new object of the desired class.
    CascadeClassifier classifier = new CascadeClassifier(classifierName);
    if (classifier == null) {
      System.err.println("Error loading classifier file \"" + classifierName + "\".");
      System.exit(1);
    }

    // The available FrameGrabber classes include OpenCVFrameGrabber (opencv_videoio),
    // DC1394FrameGrabber, FlyCapture2FrameGrabber, OpenKinectFrameGrabber, OpenKinect2FrameGrabber,
    // RealSenseFrameGrabber, PS3EyeFrameGrabber, VideoInputFrameGrabber, and FFmpegFrameGrabber.
    FrameGrabber grabber = FrameGrabber.createDefault(0);
    grabber.start();

    // CanvasFrame, FrameGrabber, and FrameRecorder use Frame objects to communicate image data.
    // We need a FrameConverter to interface with other APIs (Android, Java 2D, JavaFX, Tesseract, OpenCV, etc).
    OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

    // FAQ about IplImage and Mat objects from OpenCV:
    // - For custom raw processing of data, createBuffer() returns an NIO direct
    //   buffer wrapped around the memory pointed by imageData, and under Android we can
    //   also use that Buffer with Bitmap.copyPixelsFromBuffer() and copyPixelsToBuffer().
    // - To get a BufferedImage from an IplImage, or vice versa, we can chain calls to
    //   Java2DFrameConverter and OpenCVFrameConverter, one after the other.
    // - Java2DFrameConverter also has static copy() methods that we can use to transfer
    //   data more directly between BufferedImage and IplImage or Mat via Frame objects.
    Mat grabbedImage = converter.convert(grabber.grab());
    int height = grabbedImage.rows();
    int width = grabbedImage.cols();

    // Objects allocated with `new`, clone(), or a create*() factory method are automatically released
    // by the garbage collector, but may still be explicitly released by calling deallocate().
    // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
    Mat grayImage = new Mat(height, width, CV_8UC1);
    Mat rotatedImage = grabbedImage.clone();

    // The OpenCVFrameRecorder class simply uses the VideoWriter of opencv_videoio,
    // but FFmpegFrameRecorder also exists as a more versatile alternative.
    FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
    recorder.start();

    // CanvasFrame is a JFrame containing a Canvas component, which is hardware accelerated.
    // It can also switch into full-screen mode when called with a screenNumber.
    // We should also specify the relative monitor/camera response for proper gamma correction.
    CanvasFrame frame = new CanvasFrame("Some Title",
        CanvasFrame.getDefaultGamma() / grabber.getGamma());

    // Let's create some random 3D rotation...
    Mat randomR = new Mat(3, 3, CV_64FC1),
        randomAxis = new Mat(3, 1, CV_64FC1);
    // We can easily and efficiently access the elements of matrices and images
    // through an Indexer object with the set of get() and put() methods.
    DoubleIndexer Ridx = randomR.createIndexer(),
        axisIdx = randomAxis.createIndexer();
    axisIdx.put(0, (Math.random() - 0.5) / 4,
        (Math.random() - 0.5) / 4,
        (Math.random() - 0.5) / 4);
    Rodrigues(randomAxis, randomR);
    double f = (width + height) / 2.0;
    Ridx.put(0, 2, Ridx.get(0, 2) * f);
    Ridx.put(1, 2, Ridx.get(1, 2) * f);
    Ridx.put(2, 0, Ridx.get(2, 0) / f);
    Ridx.put(2, 1, Ridx.get(2, 1) / f);
    System.out.println(Ridx);

    // We can allocate native arrays using constructors taking an integer as argument.
    Point hatPoints = new Point(3);

    while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
      // Let's try to detect some faces! but we need a grayscale image...
      cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
      RectVector faces = new RectVector();
      classifier.detectMultiScale(grayImage, faces);
      long total = faces.size();
      for (long i = 0; i < total; i++) {
        Rect r = faces.get(i);
        int x = r.x(), y = r.y(), w = r.width(), h = r.height();
        rectangle(grabbedImage, new Point(x, y), new Point(x + w, y + h), Scalar.RED, 1, CV_AA, 0);

        // To access or pass as argument the elements of a native array, call position() before.
        hatPoints.position(0).x(x - w / 10).y(y - h / 10);
        hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
        hatPoints.position(2).x(x + w / 2).y(y - h / 2);
        fillConvexPoly(grabbedImage, hatPoints.position(0), 3, Scalar.GREEN, CV_AA, 0);
      }

      // Let's find some contours! but first some thresholding...
      threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

      // To check if an output argument is null we may call either isNull() or equals(null).
      MatVector contours = new MatVector();
      findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
      long n = contours.size();
      for (long i = 0; i < n; i++) {
        Mat contour = contours.get(i);
        Mat points = new Mat();
        approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
        drawContours(grabbedImage, new MatVector(points), -1, Scalar.BLUE);
      }

      warpPerspective(grabbedImage, rotatedImage, randomR, rotatedImage.size());

      Frame rotatedFrame = converter.convert(rotatedImage);
      frame.showImage(rotatedFrame);
      recorder.record(rotatedFrame);
    }
    frame.dispose();
    recorder.stop();
    grabber.stop();
  }
}
