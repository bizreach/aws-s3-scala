package jp.co.bizreach.s3scala

import awscala.s3.{PutObjectResult, Bucket}
import awscala.s3.{S3 => AWScalaS3}
import java.io.File
import com.amazonaws.services.s3.model._
import java.nio.file.Files
import org.joda.time.DateTime
import scala.collection.JavaConverters._
import com.amazonaws.metrics.RequestMetricCollector

/**
 * Mock implementation of S3 client which works on the local file system.
 *
 * @param dir the local root directory
 */
private[s3scala] class LocalS3Client(dir: java.io.File) extends com.amazonaws.services.s3.AmazonS3Client() with AWScalaS3 {

  // Create local root directory if it does not exist
  if(!dir.exists){
    dir.mkdirs()
  }

  override def createBucket(name: String): Bucket = super[S3].createBucket(name)

  override def putObject(putObjectRequest: PutObjectRequest): PutObjectResult = {
    val bucketName = putObjectRequest.getBucketName
    val key  = putObjectRequest.getKey
    val file = putObjectRequest.getFile

    val bucketDir = new File(dir, bucketName)
    if(!bucketDir.exists){
      throw new com.amazonaws.services.s3.model.AmazonS3Exception("All access to this object has been disabled")
    }

    val putFile = new File(bucketDir, key)
    Files.copy(file.toPath, putFile.toPath)

    // TODO set correct property value
    awscala.s3.PutObjectResult(new Bucket(bucketName), key, null, null, null, DateTime.now(), null, null)
  }

  override def getObject(getObjectRequest: GetObjectRequest): S3Object = {
    val bucketName = getObjectRequest.getBucketName
    val key        = getObjectRequest.getKey

    val bucketDir = new File(dir, bucketName)
    if(!bucketDir.exists){
      throw new com.amazonaws.services.s3.model.AmazonS3Exception("Access Denied")
    }

    val getFile = new File(bucketDir, key)
    if(!getFile.exists){
      null
    } else {
      val s3object = new S3Object()
      s3object.setBucketName(bucketName)
      s3object.setKey(key)
      s3object.setObjectContent(IOUtils.toInputStream(getFile))
      s3object
    }
  }

  override def listObjects(listObjectsRequest: ListObjectsRequest): ObjectListing = ???

  override def getS3AccountOwner(): Owner = ???

  override def listBuckets(listBucketsRequest: ListBucketsRequest): java.util.List[com.amazonaws.services.s3.model.Bucket] = {
    dir.listFiles.filter(_.isDirectory).map { child =>
      new com.amazonaws.services.s3.model.Bucket(child.getName)
    }.toList.asJava
  }

  override def getBucketLocation(getBucketLocationRequest: GetBucketLocationRequest): String = ???

  override def createBucket(createBucketRequest: CreateBucketRequest): com.amazonaws.services.s3.model.Bucket = {
    val bucketDir = new File(dir, createBucketRequest.getBucketName)
    if(bucketDir.exists){
      throw new com.amazonaws.services.s3.model.AmazonS3Exception("Your previous request to create the named bucket succeeded and you already own it.")
    }
    bucketDir.mkdir()
    new com.amazonaws.services.s3.model.Bucket(createBucketRequest.getBucketName)
  }

  override def getObjectAcl(bucketName: String, key: String, versionId: String): AccessControlList = ???

  override def setObjectAcl(bucketName: String, key: String, versionId: String, acl: AccessControlList): Unit = ???

  override def setObjectAcl(bucketName: String, key: String, versionId: String, acl: AccessControlList, requestMetricCollector: RequestMetricCollector): Unit = ???

  override def setObjectAcl(bucketName: String, key: String, versionId: String, acl: CannedAccessControlList): Unit = ???

  override def setObjectAcl(bucketName: String, key: String, versionId: String, acl: CannedAccessControlList, requestMetricCollector: RequestMetricCollector): Unit = ???

  override def getBucketAcl(bucketName: String): AccessControlList = ???

  override def getBucketAcl(getBucketAclRequest: GetBucketAclRequest): AccessControlList = ???

  override def setBucketAcl(bucketName: String, acl: AccessControlList): Unit = ???

  override def setBucketAcl(bucketName: String, acl: AccessControlList, requestMetricCollector: RequestMetricCollector): Unit = ???

  override def setBucketAcl(setBucketAclRequest: SetBucketAclRequest): Unit = ???

  override def setBucketAcl(bucketName: String, acl: CannedAccessControlList): Unit = ???

  override def setBucketAcl(bucketName: String, acl: CannedAccessControlList, requestMetricCollector: RequestMetricCollector): Unit = ???

  override def getObjectMetadata(getObjectMetadataRequest: GetObjectMetadataRequest): ObjectMetadata = ???

  override def doesBucketExist(bucketName: String): Boolean = ???

  override def getObject(getObjectRequest: GetObjectRequest, destinationFile: File): ObjectMetadata = ???

  override def deleteBucket(deleteBucketRequest: DeleteBucketRequest): Unit = {
    val bucketDir = new File(dir, deleteBucketRequest.getBucketName)
    if(!bucketDir.exists){
      throw new com.amazonaws.services.s3.model.AmazonS3Exception("Access Denied")
    }
    IOUtils.deleteDirectory(bucketDir)
  }

  override def copyObject(copyObjectRequest: CopyObjectRequest): CopyObjectResult = ???

  override def copyPart(copyPartRequest: CopyPartRequest): CopyPartResult = ???

  override def deleteObject(deleteObjectRequest: DeleteObjectRequest): Unit = {
    val bucketDir = new File(dir, deleteObjectRequest.getBucketName)
    if(!bucketDir.exists){
      throw new com.amazonaws.services.s3.model.AmazonS3Exception("Access Denied")
    }

    val deleteFile = new File(bucketDir, deleteObjectRequest.getKey)
    if(deleteFile.exists){
      deleteFile.delete()
    }
  }

  override def deleteObjects(deleteObjectsRequest: DeleteObjectsRequest): DeleteObjectsResult = ???

  override def deleteVersion(deleteVersionRequest: DeleteVersionRequest): Unit = ???

  override def setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest: SetBucketVersioningConfigurationRequest): Unit = ???

  override def getBucketVersioningConfiguration(bucketName: String): BucketVersioningConfiguration = ???

  override def getBucketWebsiteConfiguration(getBucketWebsiteConfigurationRequest: GetBucketWebsiteConfigurationRequest): BucketWebsiteConfiguration = ???

  override def getBucketLifecycleConfiguration(bucketName: String): BucketLifecycleConfiguration = ???

  override def setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest: SetBucketLifecycleConfigurationRequest): Unit = ???

  override def deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest: DeleteBucketLifecycleConfigurationRequest): Unit = ???

  override def getBucketCrossOriginConfiguration(bucketName: String): BucketCrossOriginConfiguration = ???

  override def setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest: SetBucketCrossOriginConfigurationRequest): Unit = ???

  override def deleteBucketCrossOriginConfiguration(deleteBucketCrossOriginConfigurationRequest: DeleteBucketCrossOriginConfigurationRequest): Unit = ???

  override def getBucketTaggingConfiguration(bucketName: String): BucketTaggingConfiguration = ???

  override def setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest: SetBucketTaggingConfigurationRequest): Unit = ???

  override def deleteBucketTaggingConfiguration(deleteBucketTaggingConfigurationRequest: DeleteBucketTaggingConfigurationRequest): Unit = ???

  override def setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest: SetBucketWebsiteConfigurationRequest): Unit = ???

  override def deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest: DeleteBucketWebsiteConfigurationRequest): Unit = ???

  override def setBucketNotificationConfiguration(setBucketNotificationConfigurationRequest: SetBucketNotificationConfigurationRequest): Unit = ???

  override def getBucketNotificationConfiguration(bucketName: String): BucketNotificationConfiguration = ???

  override def getBucketLoggingConfiguration(bucketName: String): BucketLoggingConfiguration = ???

  override def setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest: SetBucketLoggingConfigurationRequest): Unit = ???

  override def setBucketPolicy(bucketName: String, policyText: String): Unit = ???

  override def getBucketPolicy(getBucketPolicyRequest: GetBucketPolicyRequest): BucketPolicy = ???

  override def setBucketPolicy(setBucketPolicyRequest: SetBucketPolicyRequest): Unit = ???

  override def deleteBucketPolicy(deleteBucketPolicyRequest: DeleteBucketPolicyRequest): Unit = ???

  override def abortMultipartUpload(abortMultipartUploadRequest: AbortMultipartUploadRequest): Unit = ???

  override def completeMultipartUpload(completeMultipartUploadRequest: CompleteMultipartUploadRequest): CompleteMultipartUploadResult = ???

  override def initiateMultipartUpload(initiateMultipartUploadRequest: InitiateMultipartUploadRequest): InitiateMultipartUploadResult = ???

  override def listMultipartUploads(listMultipartUploadsRequest: ListMultipartUploadsRequest): MultipartUploadListing = ???

  override def listParts(listPartsRequest: ListPartsRequest): PartListing = ???

  override def uploadPart(uploadPartRequest: UploadPartRequest): UploadPartResult = ???

  override def restoreObject(restoreObjectRequest: RestoreObjectRequest): Unit = ???

}
