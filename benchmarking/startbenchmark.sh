#!/bin/sh
cd /Users/anshuljain/OtherProjects/spring-boot-grpc-benchmarking/benchmarking
mkdir -p ./csv-reports
var=$(date +'%Y-%d-%mT%H-%M-%S')
mkdir -p ./web-reports/"$var"
#jmeter -n -t ./jmeter-rest-vs-grpc.jmx  -l ./csv-reports/"$var".csv -e -o ./web-reports/"$var"
jmeter -n -t ./jmeter-rest-vs-grpc-db-load.jmx  -l ./csv-reports/"$var".csv -e -o ./web-reports/"$var"