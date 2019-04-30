package Classify

import java.awt.Color
import java.awt.image.{BufferedImage, Raster}
import java.io.File

object Classifier {

  // Function for getting the optimal value for converting the image into grayscale. Possible best equation used instead of (r+g+b)/3
  def pixels2gray(red: Int, green: Int, blue: Int): Int = ((0.30 * red) + (0.59 * green) + (0.11 * blue)).toInt

  //  Calculating the grayscale image for its brightness
  def calculate(img: BufferedImage): (Int) = {
    val raster: Raster = img.getRaster
    var reading: Double = 0.0

    for {h1 <- (0 until img.getHeight).toVector                     // Traversing through the image
         w1 <- (0 until img.getWidth).toVector
    } yield {
      reading += raster.getSample(w1, h1, 0)                        // Grayscale values of the entire image added
    }

    val avg = ((reading / (img.getHeight * img.getWidth * 256)) * 100).toInt // calculating the average value in percentile
    avg
  }

  // Changing the read image into Grayscale
  def makeGray(img: BufferedImage): BufferedImage = {
    for {w1 <- (0 until img.getWidth).toVector                    //Traversing through the image
         h1 <- (0 until img.getHeight).toVector
    } yield {
      val col = img.getRGB(w1, h1)
      val red = (col & 0xff0000) / 65536
      val green = (col & 0xff00) / 256
      val blue = col & 0xff
      val graycol = pixels2gray(red, green, blue)
      img.setRGB(w1, h1, new Color(graycol, graycol, graycol).getRGB)    //setting pixels into grayscale counterpart
    }
    img
  }

  def getListOfFiles(dir: String): List[File] = {                   // get all files from a directory, for reading multiple files
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
}

