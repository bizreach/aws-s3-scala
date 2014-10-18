s3-scala
==========

Scala client for AWS S3

## How to use

Add a following dependency into your build.sbt at first.

```scala
libraryDependencies += "jp.co.bizreach" %% "s3scala-scala" % "0.0.1"
```

Then you can access S3 as following:

```scala
import jp.co.bizreach.s3scala.S3
import awscala.s3._
import awscala.Region

implicit val region = Region.Tokyo
implicit val s3 = S3(accessKeyId = "xxx", secretAccessKey = "xxx")

val bucket: Bucket = s3.createBucket("unique-name-xxx")
bucket.put("sample.txt", new java.io.File("sample.txt"))
```

s3-scala also provide mock implementation which works on the local file system.

```scala
implicit val s3 = S3.local(new java.io.File("s3data"))
```

Major methods are implemented in this mock, however some methods are not implemented.
These methods throw `NotImplementedError` if invoked.
