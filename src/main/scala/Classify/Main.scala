package Classify

import com.typesafe.config._
import javax.imageio.ImageIO
import Classify.Classifier._

object Main {

  def main(args: Array[String]): Unit = {                           // main function

    val config = ConfigFactory.load()

    val destinationLocation = config.getString("Destination")      // Output Directory ; set new src/main/resources/from application.conf

    val files = getListOfFiles(config.getString("Source"))         // getting files from the source directory

    val valid = List("jpg", "jpeg", "png", "bmp")                 // List of file extensions for validating ; add new values as per your requirements

    for (file <- files) {                                           // traversing each files from the source location

      if (valid contains file.getName.split('.')(1).toString) {     // checking if the file's extension is present in the list valid or not

        val photo1 = ImageIO.read(file)                             // reading each file

        val photo2 = makeGray(photo1)                               // grayscale conversion stored

        if (calculate(photo2)<config.getInt("threshold")) {                                 // grayscaled image passed to calculate function

          // if  avg <= threshold ; writing image in the destination folder by name_dark_avg
          ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_dark_" + calculate(photo2) + ".jpg"))
        } else {
          // if avg > threshold ; writing image in the destination folder by name_bright_avg
          ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_bright_" + calculate(photo2) + ".jpg"))
        }
      }
      else println(file.getName + " is not of an image file.")    // In case the extension validation fails, console returns the print statement
    }
  }

}
