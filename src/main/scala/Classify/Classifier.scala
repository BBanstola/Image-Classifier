package Classify

import java.awt.Color
import java.awt.image.{BufferedImage, Raster}
import java.io.File
import javax.imageio.ImageIO

object Classifier {

  // Function for getting the optimal value for converting the image into grayscale. Possible best equation used instead of (r+g+b)/3
  def pixels2gray(red: Int, green: Int, blue: Int): Int = ((0.30 * red) + (0.59 * green) + (0.11 * blue)).toInt

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

  //  Calculating the grayscale image for its brightness
  def calculate(img: BufferedImage): (Int, Boolean) = {
    val raster: Raster = img.getRaster
    var reading: Double = 0.0

    for {h1 <- (0 until img.getHeight).toVector                     // Traversing through the image
         w1 <- (0 until img.getWidth).toVector
    } yield {
      reading += raster.getSample(w1, h1, 0)                        // Grayscale values of the entire image added
    }
    val avg = ((reading / (img.getHeight * img.getWidth * 256)) * 100).toInt  // calculating the average value in percentile

    if (avg <= 55) (avg, true) else (avg, false)                  // Conditions for returning Boolean values
  }

  def getListOfFiles(dir: String): List[File] = {                   // get all files from a directory, for reading multiple files
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def main(args: Array[String]): Unit = {                           // main function

    val destinationLocation: String = "./Out/"                     // Output Directory, is inside the application folder, can change path as per the liking

    val files = getListOfFiles("./In/")                            // getting files from the source directory
                                                                    // currently in the application folder, path can be changed as per liking
    val valid = List("jpg", "jpeg", "png", "bmp")                 // List of file extensions for validating ; add new values as per your requirements

    for (file <- files) {                                           // traversing each files from the source location

      if (valid contains file.getName.split('.')(1).toString) {     // checking if the file's extension is present in the list valid or not

        val photo1 = ImageIO.read(file)                             // reading each file

        val photo2 = makeGray(photo1)                               // grayscale conversion stored

        if (calculate(photo2)._2) {                                 // grayscaled image passed to calculate function

          // if the image is dark, or avg <= 55 ; writing image in the destination folder by name_dark_avg
          ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_dark_" + calculate(photo2)._1 + ".jpg"))
        } else {
          // if the image is bright, or avg > 55 ; writing image in the destination folder by name_bright_avg
          ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_bright_" + calculate(photo2)._1 + ".jpg"))
        }
      }
      else println(file.getName + " is not of an image file.")    // In case the extension validation fails, console returns the print statement
    }
  }

}

