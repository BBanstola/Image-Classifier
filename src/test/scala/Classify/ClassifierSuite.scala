package Classify

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

import Classify.Classifier._

  test ("Checking if the images are Bright or dark"){
      // Taking average as 35, and threshold as 55 then
    assert(35 <= 55 , "The image is dark")
      // Taking average as 75, and threshold as 55 then
    assert(75 > 55, "The image is bright")
  }

  test("If the images satisfies the extention test"){
    assert(List("jpg","png") contains "dark.jpg".split('.')(1), "jpg formated extentions are selected for comutaion")
    assert(!(List("jpg","png") contains "name.txt".split('.')(1)), "extensions not present in the list are not computed")
  }

}