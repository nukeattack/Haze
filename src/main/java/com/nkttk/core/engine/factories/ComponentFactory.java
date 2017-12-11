package com.nkttk.core.engine.factories;

import com.nkttk.core.components.s3.Bucket;
import com.nkttk.core.components.s3.BucketObject;
import com.nkttk.core.components.s3.S3Component;
import com.nkttk.core.engine.ComponentContainer;

public class ComponentFactory {
    public S3Component buildClient(ComponentContainer engine) {
        return new S3Component(bucketName -> new Bucket(bucketName, filename -> new BucketObject(filename)));
    }
}