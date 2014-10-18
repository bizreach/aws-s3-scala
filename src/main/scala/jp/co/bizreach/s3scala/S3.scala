package jp.co.bizreach.s3scala

import awscala.Region

object S3 {

  def apply(accessKeyId: String, secretAccessKey: String)(implicit region: Region = Region.default()): awscala.s3.S3 = {
    awscala.s3.S3(accessKeyId, secretAccessKey)
  }

  def local(dir: java.io.File): awscala.s3.S3 = {
    new LocalS3Client(dir)
  }

}
