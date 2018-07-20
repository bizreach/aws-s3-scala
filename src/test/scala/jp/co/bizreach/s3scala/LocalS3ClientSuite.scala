package jp.co.bizreach.s3scala

import org.scalatest.{BeforeAndAfter, FunSuite}
import java.io.File

import com.amazonaws.services.s3.model.{GetObjectRequest, PutObjectRequest}

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

  test("putObject"){
    val bucket = new File(dir, "test")
    bucket.mkdirs()
    // the file to upload to bucket
    val file = new File(dir, "file")
    IOUtils.write(file, "content".getBytes)

    s3.putObject(new PutObjectRequest("test", "key1", file))
    assert(new File(bucket, "key1").exists)
  }

  test("getObject"){
    val bucket = new File(dir, "test")
    bucket.mkdirs()
    // the object stored in bucket
    IOUtils.write(new File(bucket, "key1"), "content".getBytes)

    val result = s3.getObject(new GetObjectRequest("test", "key1"))
    assert(new String(IOUtils.toBytes(result.getObjectContent)) == "content")
  }

}
