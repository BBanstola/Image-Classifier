import java.io.File
import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.Raster

object Classifier {

  def pixels2gray(red: Int, green: Int, blue: Int): Int = ((0.21 * red) + (0.72 * green) + (0.07 * blue)).toInt

  def makeGray(img: BufferedImage): BufferedImage = {
    for {w1 <- (0 until img.getWidth).toVector
         h1 <- (0 until img.getHeight).toVector
    } yield {
      val col = img.getRGB(w1, h1)
      val red = (col & 0xff0000) / 65536
      val green = (col & 0xff00) / 256
      val blue = col & 0xff
      val graycol = pixels2gray(red, green, blue)
      img.setRGB(w1, h1, new Color(graycol, graycol, graycol).getRGB)
    }
    img
  }

  def calculate(img: BufferedImage): (Int, Boolean) = {
    val raster: Raster = img.getRaster
    var reading: Double = 0.0

    for {h1 <- (0 until img.getHeight).toVector
         w1 <- (0 until img.getWidth).toVector
    } yield {
      reading += raster.getSample(w1, h1, 0)
    }
    val avg = ((reading / (img.getHeight * img.getWidth * 256)) * 100).toInt

    if (avg <= 55) (avg, true) else (avg, false)
  }

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def main(args: Array[String]): Unit = {

    val destinationLocation: String = "./Out/"

    val files = getListOfFiles("./In/")

    val valid = List("jpg", "jpeg", "png", "bmp")

    for (file <- files) {

      if (valid contains file.getName.split('.')(1).toString) {

        val photo1 = ImageIO.read(file)

        val photo2 = makeGray(photo1)

        if (calculate(photo2)._2) {
          ImageIO.write(photo2, "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_dark_" + calculate(photo2)._1 + ".jpg"))
        } else {
          ImageIO.write(photo2, "jpg", new java.io.File(destinationLocation + file.getName.split('.')(0) + "_bright_" + calculate(photo2)._1 + ".jpg"))
        }
      }
      else println(file.getName + " is not of an image file.")
    }
  }

}

