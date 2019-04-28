import java.io.File
import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage

object Classifier {

  def pixels2gray(red: Int, green: Int, blue: Int): Int = ((0.21 * red) + (0.72* green) + (0.07 * blue)).toInt

  def makeGray(img: BufferedImage): BufferedImage = {
    val w = img.getWidth
    val h = img.getHeight
    for { w1 <- (0 until w).toVector
          h1 <- (0 until h).toVector
    } yield {
      val col = img.getRGB(w1, h1)
      val red =  (col & 0xff0000) / 65536
      val green = (col & 0xff00) / 256
      val blue = (col & 0xff)
      val graycol = pixels2gray(red, green, blue)
      img.setRGB(w1, h1, new Color(graycol, graycol, graycol).getRGB)
    }
    calculate(img)
  }

  def calculate(img: BufferedImage):BufferedImage = {

  }




  def main(args: Array[String]): Unit = {

    val photo1 = ImageIO.read(new File("photo.jpg"))

    val photo2 = makeGray(photo1)

    // save image to file "test.jpg"
    ImageIO.write(photo2, "jpg", new File("test.jpg"))
  }
}

