package zhaoxizhang.github.io.gsonenhance.test;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import zhaoxizhang.github.io.gsonenhance.GsonEnhance;
import zhaoxizhang.github.io.gsonenhance.GsonTypeAdapterFactory;
import zhaoxizhang.github.io.gsonenhance.model.GsonEnhanceModel;
import zhaoxizhang.github.io.gsonenhance.model.ObjectModel;


/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/25
 */
public class GsonTestManager {
    private static final String TAG = "GsonTestManager";
    private static volatile GsonTestManager instance;

    public static GsonTestManager getInstance() {
        GsonTestManager result = instance;
        if (result == null) {
            synchronized (GsonTestManager.class) {
                result = instance;
                if (result == null) {
                    result = instance = new GsonTestManager();
                }
            }
        }
        return result;
    }

    public void action() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapterFactory(new GsonTypeAdapterFactory());
        GsonEnhance.registerInstanceCreator(gsonBuilder);

        Gson gson = gsonBuilder.create();

        // test code
        ObjectModel objectModel = new ObjectModel();
        objectModel.setIntValue(1);
        objectModel.setLongValue(2);
        objectModel.setFloatValue(1f);
        objectModel.setDoubleValue(1);
        objectModel.setBoolean(true);

        GsonEnhanceModel.StaticNestedClass staticNestedClass = new GsonEnhanceModel.StaticNestedClass();
        staticNestedClass.setIntValue(1);
        staticNestedClass.setLongValue(2);
        staticNestedClass.setFloatValue(1f);
        staticNestedClass.setDoubleValue(1);
        staticNestedClass.setBoolean(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("property", "value");
        jsonObject.addProperty("number", 1);
        jsonObject.addProperty("boolean", true);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);

        List<Long> longList = new ArrayList<>();
        longList.add(1L);

        List<Float> floatList = new ArrayList<>();
        floatList.add(1f);

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.0);

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(true);
        booleanList.add(false);

        List<ObjectModel> objectModelList = new ArrayList<>();
        objectModelList.add(objectModel);

        int[] intArray = new int[]{1};

        long[] longArray = new long[]{1L};

        float[] floatArray = new float[]{1f};

        double[] doubleArray = new double[]{1.0};

        boolean[] booleanArray = new boolean[]{true, false};

        ObjectModel[] objectModelArray = new ObjectModel[]{objectModel};

        GsonEnhanceModel gsonEnhanceModel = new GsonEnhanceModel();
        gsonEnhanceModel.setIntValue(1);
        gsonEnhanceModel.setLongValue(2);
        gsonEnhanceModel.setFloatValue(1f);
        gsonEnhanceModel.setDoubleValue(1);
        gsonEnhanceModel.setBoolean(true);
        gsonEnhanceModel.setCanBoolean(true);
        gsonEnhanceModel.setIntegerObjectValue(1);
        gsonEnhanceModel.setLongObjectValue(2L);
        gsonEnhanceModel.setFloatObjectValue(1f);
        gsonEnhanceModel.setDoubleObjectValue(5.0);
        gsonEnhanceModel.setBooleanObjectValue(true);
        gsonEnhanceModel.setString("string_value");
        gsonEnhanceModel.setObjectModel(objectModel);
        gsonEnhanceModel.setStaticNestedClass(staticNestedClass);
        gsonEnhanceModel.setJsonElement(jsonObject);
        gsonEnhanceModel.setJsonObject(jsonObject);
        gsonEnhanceModel.setJsonArray(jsonArray);
        gsonEnhanceModel.setIntegerList(integerList);
        gsonEnhanceModel.setLongList(longList);
        gsonEnhanceModel.setFloatList(floatList);
        gsonEnhanceModel.setDoubleList(doubleList);
        gsonEnhanceModel.setBooleanList(booleanList);
        gsonEnhanceModel.setObjectModelList(objectModelList);
        gsonEnhanceModel.setIntArray(intArray);
        gsonEnhanceModel.setLongArray(longArray);
        gsonEnhanceModel.setFloatArray(floatArray);
        gsonEnhanceModel.setDoubleArray(doubleArray);
        gsonEnhanceModel.setBooleanArray(booleanArray);
        gsonEnhanceModel.setObjectModelArray(objectModelArray);

        String jsonString = gson.toJson(gsonEnhanceModel);
        Log.i(TAG, jsonString);

        GsonEnhanceModel gsonEnhanceModelSerialized = gson.fromJson(jsonString, GsonEnhanceModel.class);
        Log.i(TAG, gson.toJson(gsonEnhanceModelSerialized));
    }
}
