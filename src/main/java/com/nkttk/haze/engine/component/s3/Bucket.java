package com.nkttk.haze.engine.component.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 *
 */
public class Bucket {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bucket.class);

    private String name;
    private Set<FileObject> files;
    private Function<String, FileObject> bucketObjectFactory;

    public Bucket(String name, Function<String, FileObject> bucketObjectFactory) {
        this.name = name;
        this.files = new HashSet<>();
        this.bucketObjectFactory = bucketObjectFactory;
    }

    public FileObject addObject(String key) {
        LOGGER.debug("Add object {}", key);
        FileObject file = bucketObjectFactory.apply(key);
        files.add(file);
        return file;
    }

    public FileObject addObject(String name, String content) {
        LOGGER.debug("Add object {}", name);
        return addObject(name, new ByteArrayInputStream(content.getBytes()));
    }

    public FileObject addObject(String name, InputStream content) {
        LOGGER.debug("Add object {}", name);
        FileObject file = addObject(name);
        file.setContent(content);
        return file;
    }

    public String getName() {
        return name;
    }

    public Optional<FileObject> getObject(String key) {
        LOGGER.debug("Get object {}", key);
        return files.stream().filter(o -> o.getKey().equals(key)).findFirst();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bucket) {
            return name.equals(((Bucket) obj).getName());
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return "Bucket[" + name + "]";
    }
}