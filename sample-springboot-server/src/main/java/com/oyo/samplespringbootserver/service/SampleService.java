package com.oyo.samplespringbootserver.service;

import java.util.List;

import com.oyo.samplegrpcserver.service.LargeProtoObject;
import com.oyo.samplespringbootserver.model.LargePojoObject;

public interface SampleService
{
    List<Integer> getRandomNumbers(int count);

    List<LargeProtoObject> getLargeProtoObjects(int count);

    List<LargePojoObject> getLargePojoObjects(int count);
}
