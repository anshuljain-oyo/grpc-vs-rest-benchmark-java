package com.oyo.sampleclient.sample;

public interface SampleRestClientService
{
    void getRandomNumbers(int count);

    void getLargeObjectsProto(int count);

    void getLargeObjectsJson(int count);

  void getHotelDataObjectsProto(int count);
}
