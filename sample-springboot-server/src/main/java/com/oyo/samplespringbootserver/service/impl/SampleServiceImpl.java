package com.oyo.samplespringbootserver.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.oyo.samplegrpcserver.service.Field36;
import com.oyo.samplegrpcserver.service.Field37;
import com.oyo.samplegrpcserver.service.Field5;
import com.oyo.samplegrpcserver.service.Field54;
import com.oyo.samplegrpcserver.service.Field6;
import com.oyo.samplegrpcserver.service.Field83;
import com.oyo.samplegrpcserver.service.Field84;
import com.oyo.samplegrpcserver.service.Field85;
import com.oyo.samplegrpcserver.service.LargeProtoObject;
import com.oyo.samplespringbootserver.model.LargePojoObject;
import com.oyo.samplespringbootserver.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService
{
    private static final Map<Integer, List<LargeProtoObject>> LARGE_OBJECT_CACHE = new HashMap<>();
    private static final Map<Integer, List<LargePojoObject>> LARGE_OBJECT_AS_JSON_CACHE = new HashMap<>();
    private static final Random RANDOM_GENERATOR = new Random();

    @Override
    public List<Integer> getRandomNumbers(int count)
    {
        return generateRandomNumbers(count);
    }

    @Override
    public List<LargeProtoObject> getLargeProtoObjects(int count)
    {
        if (!LARGE_OBJECT_CACHE.containsKey(count))
        {
            List<LargeProtoObject> largeObjects = generateLargeProtoObjects(count);
            LARGE_OBJECT_CACHE.put(count, largeObjects);
        }

        return LARGE_OBJECT_CACHE.get(count);
    }

    @Override
    public List<LargePojoObject> getLargePojoObjects(int count)
    {
        if (!LARGE_OBJECT_AS_JSON_CACHE.containsKey(count))
        {
            List<LargePojoObject> largePojoObjects = generateLargePojoObjects(count);
            LARGE_OBJECT_AS_JSON_CACHE.put(count, largePojoObjects);
        }

        return LARGE_OBJECT_AS_JSON_CACHE.get(count);
    }

    private List<Integer> generateRandomNumbers(int count)
    {
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            randomNumbers.add(RANDOM_GENERATOR.nextInt(10) + 1);
        }
        return randomNumbers;
    }

    private List<LargeProtoObject> generateLargeProtoObjects(int count)
    {
        List<LargeProtoObject> largeObjects = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            largeObjects.add(generateLargeProtoObject());
        }
        return largeObjects;
    }

    private List<LargePojoObject> generateLargePojoObjects(int count)
    {
        List<LargePojoObject> largePojoObjects = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            largePojoObjects.add(generateLargePojoObject());
        }
        return largePojoObjects;
    }

    private LargeProtoObject generateLargeProtoObject()
    {
        return LargeProtoObject.newBuilder()
                .setField1(1L)
                .setField2(1L)
                .setField3(1L)
                .setField4(1)
                .setField5(Field5.FIELD_5_0)
                .setField6(Field6.FIELD_6_0)
                .setField7("S1")
                .setField8("S1")
                .setField9(1)
                .setField10(1L)
                .setField11(1L)
                .setField12(1L)
                .setField13(1L)
                .setField14(1L)
                .setField15(1L)
                .setField16(1L)
                .setField17(1L)
                .setField18(1L)
                .setField19(1)
                .setField20("S1")
                .setField21("S1")
                .setField22(1)
                .setField23(1L)
                .setField24(1L)
                .setField25(1L)
                .setField26(1L)
                .setField27(1L)
                .setField28(1L)
                .setField29(1L)
                .setField30(false)
                .setField31(false)
                .setField32(false)
                .setField33(false)
                .setField34(false)
                .setField35(false)
                .setField36(Field36.FIELD_36_0)
                .setField37(Field37.FIELD_37_0)
                .setField38(1)
                .setField39(1)
                .setField40(1)
                .setField41(1)
                .setField42("S1")
                .setField43(1)
                .setField44("S1")
                .setField45(1)
                .setField46(1)
                .setField47("S1")
                .setField48(1)
                .setField49("S1")
                .setField50(1)
                .setField51("S1")
                .setField52(1)
                .setField53(false)
                .setField54(Field54.FIELD_54_0)
                .setField55(1L)
                .setField56(1)
                .setField57("S1")
                .setField58("S1")
                .setField59(1)
                .setField60(1)
                .setField61("S1")
                .setField62("S1")
                .setField63("S1")
                .setField64(1)
                .setField65(false)
                .setField66(false)
                .setField67("S1")
                .setField68("S1")
                .setField69("S1")
                .setField70("S1")
                .setField71("S1")
                .setField72("S1")
                .setField73(false)
                .setField74(1L)
                .setField75(1L)
                .setField76(1L)
                .setField77(1L)
                .setField78(1L)
                .setField79(1L)
                .setField80(1L)
                .setField81(1L)
                .setField82(1L)
                .addAllField83(generateField83s())
                .addAllField84(generateField84s())
                .addAllField85(generateField85s())
                .build();
    }

    private LargePojoObject generateLargePojoObject()
    {
        LargePojoObject largePojoObject = new LargePojoObject();
        largePojoObject.setField1(1L);
        largePojoObject.setField2(1L);
        largePojoObject.setField3(1L);
        largePojoObject.setField4(1);
        largePojoObject.setField7("S1");
        largePojoObject.setField8("S1");
        largePojoObject.setField9(1);
        largePojoObject.setField10(1L);
        largePojoObject.setField11(1L);
        largePojoObject.setField12(1L);
        largePojoObject.setField13(1L);
        largePojoObject.setField14(1L);
        largePojoObject.setField15(1L);
        largePojoObject.setField16(1L);
        largePojoObject.setField17(1L);
        largePojoObject.setField18(1L);
        largePojoObject.setField19(1);
        largePojoObject.setField20("S1");
        largePojoObject.setField21("S1");
        largePojoObject.setField22(1);
        largePojoObject.setField23(1L);
        largePojoObject.setField24(1L);
        largePojoObject.setField25(1L);
        largePojoObject.setField26(1L);
        largePojoObject.setField27(1L);
        largePojoObject.setField28(1L);
        largePojoObject.setField29(1L);
        largePojoObject.setField30(false);
        largePojoObject.setField31(false);
        largePojoObject.setField32(false);
        largePojoObject.setField33(false);
        largePojoObject.setField34(false);
        largePojoObject.setField35(false);
        largePojoObject.setField38(1);
        largePojoObject.setField39(1);
        largePojoObject.setField40(1);
        largePojoObject.setField41(1);
        largePojoObject.setField42("S1");
        largePojoObject.setField43(1);
        largePojoObject.setField44("S1");
        largePojoObject.setField45(1);
        largePojoObject.setField46(1);
        largePojoObject.setField47("S1");
        largePojoObject.setField48(1);
        largePojoObject.setField49("S1");
        largePojoObject.setField50(1);
        largePojoObject.setField51("S1");
        largePojoObject.setField52(1);
        largePojoObject.setField53(false);
        largePojoObject.setField55(1L);
        largePojoObject.setField56(1);
        largePojoObject.setField57("S1");
        largePojoObject.setField58("S1");
        largePojoObject.setField59(1);
        largePojoObject.setField60(1);
        largePojoObject.setField61("S1");
        largePojoObject.setField62("S1");
        largePojoObject.setField63("S1");
        largePojoObject.setField64(1);
        largePojoObject.setField65(false);
        largePojoObject.setField66(false);
        largePojoObject.setField67("S1");
        largePojoObject.setField68("S1");
        largePojoObject.setField69("S1");
        largePojoObject.setField70("S1");
        largePojoObject.setField71("S1");
        largePojoObject.setField72("S1");
        largePojoObject.setField73(false);
        largePojoObject.setField74(1L);
        largePojoObject.setField75(1L);
        largePojoObject.setField76(1L);
        largePojoObject.setField77(1L);
        largePojoObject.setField78(1L);
        largePojoObject.setField79(1L);
        largePojoObject.setField80(1L);
        largePojoObject.setField81(1L);
        largePojoObject.setField82(1L);
        return largePojoObject;
    }

    private List<Field83> generateField83s()
    {
        List<Field83> field83s = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            field83s.add(generateField83());
        }
        return field83s;
    }

    private List<Field84> generateField84s()
    {
        List<Field84> field84s = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            field84s.add(generateField84());
        }
        return field84s;
    }

    private List<Field85> generateField85s()
    {
        List<Field85> field85s = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            field85s.add(generateField85());
        }
        return field85s;
    }

    private Field83 generateField83()
    {
        return Field83.newBuilder()
                .setField1(1L)
                .setField2(1L)
                .setField3(1L)
                .setField4("S1")
                .setField5("S1")
                .build();
    }

    private Field84 generateField84()
    {
        return Field84.newBuilder()
                .setField1(1L)
                .setField2(1L)
                .setField3(1L)
                .setField4("S1")
                .setField5("S1")
                .build();
    }

    private Field85 generateField85()
    {
        return Field85.newBuilder()
                .setField1(1L)
                .setField2(1L)
                .setField3(1L)
                .setField4("S1")
                .setField5("S1")
                .build();
    }
}
