package com.oyo.samplegrpcserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SampleServiceImpl extends SampleGrpc.SampleImplBase {

  private static final Random RANDOM_GENERATOR = new Random();

  @Override
  public void randomNumber(SampleRequest request, StreamObserver<SampleResponse> responseObserver) {
    int count = request.getCount();

    List<Integer> randomNumbers = getRandomNumbers(count);

    SampleResponse response = SampleResponse.newBuilder()
        .addAllRandomNumber(randomNumbers)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  public List<Integer> getRandomNumbers(int count) {
    return generateRandomNumbers(count);
  }

  private List<Integer> generateRandomNumbers(int count) {
    List<Integer> randomNumbers = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      randomNumbers.add(RANDOM_GENERATOR.nextInt(10) + 1);
    }
    return randomNumbers;
  }
}
