package jp.co.bizreach.s3scala

import awscala.CredentialsProvider
import awscala.s3.S3Client

object S3 {

  def apply(accessKeyId: String, secretAccessKey: String)(implicit region: awscala.Region): awscala.s3.S3 = {
    awscala.s3.S3(accessKeyId, secretAccessKey)
  }

  def apply(credentialsProvider: CredentialsProvider)(implicit region: awscala.Region): awscala.s3.S3 = {
    awscala.s3.S3(credentialsProvider)
  }

  def apply()(implicit region: awscala.Region): awscala.s3.S3 = {
    awscala.s3.S3()
  }

  def local(dir: java.io.File): awscala.s3.S3 = {
    new LocalS3Client(dir)
  }

  def async()(implicit region: awscala.Region): S3AsyncClient = {
    val client = awscala.s3.S3()
    new S3AsyncClient(client.asInstanceOf[S3Client])
  }

  def async(accessKeyId: String, secretAccessKey: String)(implicit region: awscala.Region): S3AsyncClient = {
    val client = awscala.s3.S3(accessKeyId, secretAccessKey)
    new S3AsyncClient(client.asInstanceOf[S3Client])
  }

}
