package com.oyo.sampleclient.sample;

public interface SampleGrpcClientService
{
    void getRandomNumbers(int count);

    void getLargeObjectsProto(int count);

    void getHotelDataObjectsProto(int count);
}
