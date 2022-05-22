package com.oyo.sampleclient.sample.impl;

import org.springframework.stereotype.Service;
import com.oyo.sampleclient.sample.SampleGrpcClientService;
import com.oyo.samplegrpcserver.service.GetRandomHotelDataObjectsRequest;
import com.oyo.samplegrpcserver.service.GetRandomHotelDataObjectsResponse;
import com.oyo.samplegrpcserver.service.HotelDataProtoServiceGrpc;
import com.oyo.samplegrpcserver.service.LargeProtoObjectRequest;
import com.oyo.samplegrpcserver.service.LargeProtoObjectResponse;
import com.oyo.samplegrpcserver.service.LargeProtoObjectServiceGrpc;
import com.oyo.samplegrpcserver.service.SampleGrpc;
import com.oyo.samplegrpcserver.service.SampleRequest;
import com.oyo.samplegrpcserver.service.SampleResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class SampleGrpcClientServiceImpl implements SampleGrpcClientService {

  @GrpcClient("grpc-samplegrpc-service")
  SampleGrpc.SampleBlockingStub sampleGrpcSynchronousStub;

  @GrpcClient("grpc-largeprotoobject-service")
  LargeProtoObjectServiceGrpc.LargeProtoObjectServiceBlockingStub largeProtoObjectServiceSynchronousStub;

  @GrpcClient("grpc-hoteldataproto-service")
  HotelDataProtoServiceGrpc.HotelDataProtoServiceBlockingStub hotelDataProtoServiceBlockingStub;

  @Override
  public void getRandomNumbers(int count) {
    SampleRequest request = SampleRequest.newBuilder().setCount(count).build();
    SampleResponse sampleResponse = sampleGrpcSynchronousStub.randomNumber(request);
  }

  public void getLargeObjectsProto(int count) {
    LargeProtoObjectRequest request = LargeProtoObjectRequest.newBuilder().setCount(count).build();
    LargeProtoObjectResponse largeObjects = largeProtoObjectServiceSynchronousStub
        .getLargeProtoObjects(request);
  }

  @Override
  public void getHotelDataObjectsProto(int count) {
    GetRandomHotelDataObjectsRequest request = GetRandomHotelDataObjectsRequest.newBuilder()
        .setCount(count).build();
    GetRandomHotelDataObjectsResponse response = hotelDataProtoServiceBlockingStub.getRandomHotelDataObjects(
        request);
  }
}
