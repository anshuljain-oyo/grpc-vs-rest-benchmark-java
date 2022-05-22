package com.oyo.samplespringbootserver.model;

import java.util.List;

public class LargeObjectJsonResponse
{
    List<LargePojoObject> largePojoObjects;

    public List<LargePojoObject> getLargeObjectPOJOS()
    {
        return largePojoObjects;
    }

    public void setLargeObjectPOJOS(List<LargePojoObject> largePojoObjects)
    {
        this.largePojoObjects = largePojoObjects;
    }
}
