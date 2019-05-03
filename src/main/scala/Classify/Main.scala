package Classify

import javax.imageio.ImageIO
import Classify.Classifier._
import pureconfig.error.ConfigReaderFailures
import pureconfig.loadConfig
import pureconfig.generic.auto._

object Main {

  def main(args: Array[String]): Unit = {                                      // main function

    //val config = ConfigFactory.load()

    val simpleConfig: Either[ConfigReaderFailures, Image] = loadConfig[Image]

    simpleConfig match {
      case Left(ex) => ex.toList.foreach(println)

      case Right(config) =>

        val destinationLocation = config.image.destination // Output Directory ; set new src/main/resources/from application.conf

        val files = getListOfFiles(config.image.source) // getting files from the source directory

        for (file <- files) {
          // traversing each files from the source location

          val photo1 = ImageIO.read(file) // reading each file

          val photo2 = makeGray(photo1) // grayscale conversion stored

          if (calculate(photo2) < config.image.threshold) {
            // grayscaled image passed to calculate function

            // if  avg <= threshold ; writing image in the destination folder by name_dark_avg
            ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.take(file.getName.lastIndexOf('.')) + "_dark_" + calculate(photo2) + ".jpg"))
          } else {
            // if avg > threshold ; writing image in the destination folder by name_bright_avg
            ImageIO.write(ImageIO.read(file), "jpg", new java.io.File(destinationLocation + file.getName.take(file.getName.lastIndexOf('.')) + "_bright_" + calculate(photo2) + ".jpg"))
          }
        }
      }
    }

}
