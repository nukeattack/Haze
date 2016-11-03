package com.nkttk.engine.components.s3;


import com.nkttk.engine.components.events.BucketEvent;
import com.nkttk.engine.components.events.BucketEventType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 */
public class FileServerEngine {
  private List<EventSubscription> eventSubscriptions = new LinkedList<>();
  private Map<String, Bucket> buckets = new HashMap<>();

  public void addBucket(String name){
    buckets.put(name, new Bucket(name));
  }

  public BucketObject addFile(String bucket, String filename, String content){
    BucketObject bucketObject = getBucket(bucket).addFile(filename, content);
    for(EventSubscription subscription : eventSubscriptions){
      if(subscription.getEventType() == BucketEventType.PUT){
        subscription.getSubscriber().accept(new BucketEvent(bucketObject, BucketEventType.PUT));
      }
    }
    return bucketObject;
  }

  public Bucket getBucket(String name){
    if(!buckets.containsKey(name))throw new RuntimeException("Bucket not found : " + name);
    return buckets.get(name);
  }

  public void addEventSubscription(Bucket bucket, BucketEventType type, Consumer<BucketEvent> subscription){
    eventSubscriptions.add(new EventSubscription(bucket, type, subscription));
  }
}
