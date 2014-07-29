package jp.co.bizreach.s3scala

import org.scalatest.{BeforeAndAfter, FunSuite}
import java.io.File

class LocalS3ClientSuite extends FunSuite with BeforeAndAfter {

  val dir = new File("data")
  implicit var s3: awscala.s3.S3 = null

  before {
    s3 = S3.local(dir)
  }

  after {
    IOUtils.deleteDirectory(dir)
  }

  test("createBucket"){
    s3.createBucket("test")
    assert(new File(dir, "test").exists)
  }

}
