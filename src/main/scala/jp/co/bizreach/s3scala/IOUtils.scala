package jp.co.bizreach.s3scala

import java.io._

/**
 * Provides utility methods which are used s3-scala internally.
 */
private[s3scala] object IOUtils {

  def toInputStream(file: File): InputStream = {
    val in = new FileInputStream(file)
    val out = new ByteArrayOutputStream()
    try {
      var length = 0
      var buf = new Array[Byte](1024 * 8)
      while(length != -1){
        length = in.read(buf)
        if(length > 0){
          out.write(buf, 0, length)
        }
      }
    } finally {
      in.close()
    }
    new ByteArrayInputStream(out.toByteArray)
  }

  def toBytes(in: InputStream): Array[Byte] = {
    try {
      val out = new ByteArrayOutputStream()

      var length = 0
      var buf = new Array[Byte](1024 * 8)
      while(length != -1){
        length = in.read(buf)
        if(length > 0){
          out.write(buf, 0, length)
        }
      }

      out.toByteArray
    } finally {
      in.close()
    }
  }

  def write(file: File, bytes: Array[Byte]): Unit = {
    val out = new FileOutputStream(file)
    try {
      out.write(bytes)
    } finally {
      out.close()
    }
  }

  def deleteDirectory(dir: File): Unit = {
    dir.listFiles.foreach { file =>
      if(file.isDirectory){
        deleteDirectory(file)
      } else {
        file.delete()
      }
    }
    dir.delete()
  }

}
