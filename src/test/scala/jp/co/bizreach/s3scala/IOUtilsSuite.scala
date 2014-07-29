package jp.co.bizreach.s3scala

import org.scalatest.FunSuite
import java.io.File

class IOUtilsSuite extends FunSuite {

  test("toInputStream and toBytes"){
    val file = new File("README.md")
    val in = IOUtils.toInputStream(file)
    val bytes = IOUtils.toBytes(in)
    assert(file.length == bytes.length)
  }

  test("deleteDirectory"){
    val dir = new File("data")
    dir.mkdir()
    val file = new File(dir, "test.txt")
    file.createNewFile()

    assert(dir.exists())
    assert(file.exists())

    IOUtils.deleteDirectory(dir)

    assert(!dir.exists())
    assert(!file.exists())
  }

}
