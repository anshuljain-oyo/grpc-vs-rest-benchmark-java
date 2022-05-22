package com.oyo.samplegrpcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServerApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(GrpcServerApplication.class, args);
//    Server server = ServerBuilder.forPort(3000)
//        .addService(new SampleServiceImpl())
//        .addService(new LargeProtoObjectServiceImpl())
//        .addService(new HotelDataServiceImpl())
//        .build();
//
//    server.start();
//
//    System.out.println("gRPC Server started.");
//
//    server.awaitTermination();
  }
}
