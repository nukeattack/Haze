package com.nkttk.core.components.s3;

import com.nkttk.core.components.events.BucketEventType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class S3EngineTest {
  @Test
  public void testAddFile() {
    S3Engine engine = new S3Engine();
    String bucketName = "sample_bucket";
    engine.addBucket(bucketName);
    String fileName = "sample.txt";
    String fileContent = "bla bla bla\nbla bla\n";
    BucketObject file = engine.addFile(bucketName, fileName, fileContent);
    System.out.println(new String(file.getContent()));
  }

  @Test
  public void testEventSubscription() {
    S3Engine engine = new S3Engine();
    String bucketName = "sample_bucket";
    Bucket bucket = engine.addBucket(bucketName);
    String fileName = "sample.txt";
    String fileContent = "bla bla bla\nbla bla\n";
    StringBuilder resultFileName = new StringBuilder();
    engine.addEventSubscription(bucket, BucketEventType.PUT, x -> resultFileName.append(x.getBucketObject().getKey()));
    BucketObject file = engine.addFile(bucketName, fileName, fileContent);
    Assert.assertEquals(resultFileName.toString(), file.getKey());
  }

}