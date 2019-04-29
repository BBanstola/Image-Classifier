package Classify

import java.io.File
import javax.imageio.ImageIO

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

import Classify.Classifier._

  // reading two images, perfectly black and perfectly white for testing purpose
  val p1 = ImageIO.read(new File("dark.jpg"))
  val p2 = ImageIO.read(new File("bright.jpg"))

  test ("Checking if the images are Dark"){
    assert(calculate(p1)._2, "The image p1 is darker in nature")
    assert(!calculate(p2)._2, "The image p2 is not darker in nature")
  }

  test ("Checking if the images are Bright"){
    assert(calculate(p1)._1 <= 55 , "The image p1 is not brighter in nature")
    assert(calculate(p2)._1 > 55, "The image p2 is brighter in nature")
  }

  test("If the images satisfies the extention test"){
    assert(List("jpg","png") contains "dark.jpg".split('.')(1), "jpg formated extentions are selected for comutaion")
    assert(!(List("jpg","png") contains "name.txt".split('.')(1)), "extensions not present in the list are not computed")
  }

}