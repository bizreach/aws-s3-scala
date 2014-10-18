package jp.co.bizreach.s3scala

object S3 {

  def apply(accessKeyId: String, secretAccessKey: String)(implicit region: awscala.Region): awscala.s3.S3 = {
    awscala.s3.S3(accessKeyId, secretAccessKey)
  }

  def apply()(implicit region: awscala.Region): awscala.s3.S3 = {
    awscala.s3.S3()
  }

  def local(dir: java.io.File): awscala.s3.S3 = {
    new LocalS3Client(dir)
  }

}
