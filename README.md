# Spring Boot and gRPC Benchmarking

# 🛠Project Structures
```bash
sample-client/src
├── main
│   ├── java
│   │   └── com
│   │       └── oyo
│   │           └── sampleclient
│   │               └── sample
│   │                   └── impl
│   └── resources
└── test
    └── java
        └── com
            └── oyo
                └── sampleclient
sample-grpc-codegen/src
└── main
    ├── java
    │   └── model
    └── proto
sample-grpc-server/src
└── main
    ├── java
    │   └── com
    │       └── oyo
    │           └── samplegrpcserver
    │               ├── controller
    │               ├── model
    │               ├── repository
    │               └── service
    └── resources
sample-springboot-server/src
├── main
│   ├── java
│   │   └── com
│   │       └── oyo
│   │           └── samplespringbootserver
│   │               ├── config
│   │               ├── controller
│   │               ├── enums
│   │               ├── model
│   │               ├── repository
│   │               └── service
│   │                   └── impl
│   └── resources
└── test
    └── java
        └── com
            └── oyo
                └── samplespringbootserver
```

*Single Spring Boot client backed by a GRPC and a Spring Boot Servers.* 
<br/>

## service-client

A Spring Boot project that accepts JSON and Protocol Buffer responses.  

**Port:** 5001
<br/>
**Endpoints**

```
// REST

/rest/randomNumbers?count={n} -> Generates {n} random numbers as JSON List
/rest/largeObjects?count={n} -> Generates {n} LargeObjects as Protocol Buffer Object
/rest/largeObjects/json?count={n} -> Generates {n} LargeObjectPOJOs as JSON List

// GRPC

/grpc/randomNumbers?count={n} -> Generates {n} random numbers as Protocol Buffer Object
/grpc/largeObjects?count={n} -> Generates {n} LargeObjects as Protocol Buffer Object
```

- `/rest/**` calls are handled by `service-springboot-server`  
- `/grpc/**` calls are handled by  `service-grpc-server`

> This project has `service-grpc-codegen` as dependency in its `pom.xml`.

<br/>

## service-springboot-server

<br/>
A basic Spring Boot project.

**Port:** 4000
<br/>
**Endpoints**

```
/rest/randomNumbers?count={n} -> Generates {n} random numbers as JSON List
/rest/largeObjects?count={n} -> Generates {n} LargeObjects as Protocol Buffer Object
/rest/largeObjects/json?count={n} -> Generates {n} LargeObjectPOJOs as JSON List
```

<br/>

## service-grpc-server

<br/>
A basic gRPC Server.

**Port:** 3000
<br/>
**Endpoints**

```
/grpc/randomNumbers?count={n} -> Generates {n} random numbers as Protocol Buffer Object
/grpc/largeObjects?count={n} -> Generates {n} LargeObjects as Protocol Buffer Object
```

<br/>

> This project has `service-grpc-codegen` as dependency in its `pom.xml`.

<br/>

## service-grpc-codegen

<br/>
This project is the gist of the gRPC part of the main project.   

The sole purpose of this project is to generate the code based on the given Protobuff file to enable the Server (*service-grpc-server*) and the Client (*service-client*) to make Remote Procedure Calls (RPC) as if the methods they call are local methods.  

For this project, there's a `Sample.proto` and a `LargeObject.proto`  file located under `/src/proto/`.   

With the help of plugins, whenever the project is compiled and installed, it generates the required code (hence we call it ***codegen***) under `/targets/generated-sources` folder.
<br/>

# ⏱ Benchmarking

This benchmarking compares the **performances** of **"gRPC with Protocol Buffers"** against **"REST with JSON"** during **data transportation**.

<br/>
