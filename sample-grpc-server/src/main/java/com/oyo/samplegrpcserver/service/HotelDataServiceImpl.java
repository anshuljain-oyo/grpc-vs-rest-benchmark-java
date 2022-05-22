package com.oyo.samplegrpcserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import com.oyo.samplegrpcserver.model.HotelData;
import com.oyo.samplegrpcserver.repository.HotelDataRepo;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HotelDataServiceImpl extends HotelDataProtoServiceGrpc.HotelDataProtoServiceImplBase {

  private static final int MIN_ID = 1;
  private static final int MAX_ID = 134497;
  private static final Random random = new Random();
  private static final Map<Integer, List<HotelDataObject>> LARGE_OBJECT_CACHE = new HashMap<>();

  private final HotelDataRepo hotelDataRepo;

  public HotelDataServiceImpl(HotelDataRepo hotelDataRepo) {
    this.hotelDataRepo = hotelDataRepo;
  }

  @Override
  public void getRandomHotelDataObjects(GetRandomHotelDataObjectsRequest request,
      StreamObserver<GetRandomHotelDataObjectsResponse> responseObserver) {
    List<HotelDataObject> hotelDataObjectList = getHotelDataObjectsList(request.getCount());
    GetRandomHotelDataObjectsResponse response = GetRandomHotelDataObjectsResponse.newBuilder()
        .addAllHotelDataObjects(hotelDataObjectList)
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  private List<HotelDataObject> getHotelDataObjectsList(int count) {
    if (!LARGE_OBJECT_CACHE.containsKey(count)) {
      List<HotelData> hotelDataList = random.ints(MIN_ID, MAX_ID).distinct()
          .limit(count)
          .boxed()
          .map(id -> hotelDataRepo.findById(id).orElse(getDefaultHotelData(id)))
          .collect(Collectors.toList());
      List<HotelDataObject> hotelDataObjects = hotelDataList.stream()
          .map(this::convertHotelDataEntityToHotelDataObject)
          .collect(Collectors.toList());
      LARGE_OBJECT_CACHE.put(count, hotelDataObjects);
    }

    return LARGE_OBJECT_CACHE.get(count);
  }

  @Override
  public void getRandomHotelDataObject(Empty request,
      StreamObserver<GetRandomHotelDataObjectResponse> responseObserver) {
    int id = random.nextInt(MAX_ID) + MIN_ID;
    GetRandomHotelDataObjectResponse response = GetRandomHotelDataObjectResponse.newBuilder()
        .setHotelDataObject(
            convertHotelDataEntityToHotelDataObject(
                hotelDataRepo.findById(id).orElse(getDefaultHotelData(id))))
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  private HotelData getDefaultHotelData(int id) {
    HotelData hotelData = new HotelData();
    hotelData.setId(id);
    hotelData.setHotelId(-1);
    hotelData.setCountryId(-1);
    hotelData.setHotelType(HotelType.hotel);
    hotelData.setWizardEnabled(false);
    hotelData.setOyoId("Default");
    hotelData.setGeoLocation("Default");
    hotelData.setIsCpEnabled(false);
    return hotelData;
  }

  private HotelDataObject convertHotelDataEntityToHotelDataObject(
      HotelData hdObj) {
    HotelType hotelType =
        Objects.isNull(hdObj.getHotelType()) ? HotelType.hotel : hdObj.getHotelType();
    boolean wizardEnabled = Objects.nonNull(hdObj.getWizardEnabled()) && hdObj.getWizardEnabled();
    boolean isCpEnabled = Objects.nonNull(hdObj.getIsCpEnabled()) && hdObj.getIsCpEnabled();
    String oyoId = Objects.isNull(hdObj.getOyoId()) ? "" : hdObj.getOyoId();
    String geoLocation = Objects.isNull(hdObj.getGeoLocation()) ? "" : hdObj.getGeoLocation();
    return HotelDataObject.newBuilder()
        .setId(hdObj.getId())
        .setHotelId(hdObj.getHotelId())
        .setCountryId(hdObj.getCountryId())
        .setHotelType(hotelType)
        .setWizardEnabled(wizardEnabled)
        .setOyoId(oyoId)
        .setGeoLocation(geoLocation)
        .setIsCpEnabled(isCpEnabled)
        .build();
  }
}
