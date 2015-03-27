package jp.co.bizreach.s3scala

import java.io.File

import awscala.s3.S3Client
import com.amazonaws.event.{ProgressEventType, ProgressEvent, ProgressListener}
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.{Upload, TransferManager}

import scala.concurrent.{Future, Promise}


class S3AsyncClient(client: S3Client) {

  val tm = new TransferManager(client)


  def upload(bucketName: String, key: String, file: File): Future[Upload] = {
    upload(new PutObjectRequest(bucketName, key, file))
  }


  def upload(request: PutObjectRequest): Future[Upload] = {
    val up = tm.upload(request)
    val p = Promise[Upload]()
    up.addProgressListener(new ProgressListener {
      override def progressChanged(progressEvent: ProgressEvent): Unit = {
        // onCompleted
        if(ProgressEventType.TRANSFER_COMPLETED_EVENT == progressEvent.getEventType) {
          p trySuccess up
        }
      }
    })

    // if upload is done before fire progress listener event.
    if(up.isDone) {
      p trySuccess up
    }

    p.future
  }

}

