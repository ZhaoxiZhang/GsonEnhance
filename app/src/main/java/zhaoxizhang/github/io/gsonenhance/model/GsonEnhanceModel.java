package zhaoxizhang.github.io.gsonenhance.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import zhaoxizhang.github.io.gson_enhance_annotation.GsonNewInstance;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapter;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapterTest;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/25
 */
@GsonNewInstance
@GsonTypeAdapter
public class GsonEnhanceModel extends GsonEnhanceSuperModel {
    @SerializedName(value = "int_value", alternate = {"alternate_int_value", "int_value_alternate"})
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private boolean isBoolean;
    private boolean canBoolean;
    private Integer integerObjectValue;
    private Long longObjectValue;
    private Float floatObjectValue;
    private Double doubleObjectValue;
    private Boolean booleanObjectValue;
    private String string;
    private ObjectModel objectModel;
    private StaticNestedClass staticNestedClass;
    private JsonElement jsonElement;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private List<Integer> integerList;
    private List<Long> longList;
    private List<Float> floatList;
    private List<Double> doubleList;
    private List<Boolean> booleanList;
    private List<String> stringList;
    private List<ObjectModel> objectModelList;
    private List<StaticNestedClass> staticNestedClassList;
    private List<JsonObject> jsonObjectList;
    private int[] intArray;
    private long[] longArray;
    private float[] floatArray;
    private double[] doubleArray;
    private boolean[] booleanArray;
    private Integer[] integerObjectArray;
    private Long[] longObjectArray;
    private Float[] floatObjectArray;
    private Double[] doubleObjectArray;
    private Boolean[] booleanObjectArray;
    private String[] stringArray;
    private ObjectModel[] objectModelArray;
    private StaticNestedClass[] staticNestedClassArray;
    private JsonObject[] jsonObjectArray;
    private List<String>[] listStringArray;
    private List<List<String>>[] listListStringArray;

    private Map<String, List<ObjectModel>>[] mapStringArrayModelArray;

    @GsonTypeAdapter
    public static class StaticNestedClass {
        private int intValue;
        private long longValue;
        private float floatValue;
        private double doubleValue;
        private boolean isBoolean;
        private boolean canBoolean;
        private List<String> stringList;
        private List<ObjectModel> objectModelList;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public long getLongValue() {
            return longValue;
        }

        public void setLongValue(long longValue) {
            this.longValue = longValue;
        }

        public float getFloatValue() {
            return floatValue;
        }

        public void setFloatValue(float floatValue) {
            this.floatValue = floatValue;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public boolean isBoolean() {
            return isBoolean;
        }

        public void setBoolean(boolean aBoolean) {
            isBoolean = aBoolean;
        }

        public boolean isCanBoolean() {
            return canBoolean;
        }

        public void setCanBoolean(boolean canBoolean) {
            this.canBoolean = canBoolean;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public List<ObjectModel> getObjectModelList() {
            return objectModelList;
        }

        public void setObjectModelList(List<ObjectModel> objectModelList) {
            this.objectModelList = objectModelList;
        }
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        isBoolean = aBoolean;
    }

    public boolean isCanBoolean() {
        return canBoolean;
    }

    public void setCanBoolean(boolean canBoolean) {
        this.canBoolean = canBoolean;
    }

    public Integer getIntegerObjectValue() {
        return integerObjectValue;
    }

    public void setIntegerObjectValue(Integer integerObjectValue) {
        this.integerObjectValue = integerObjectValue;
    }

    public Long getLongObjectValue() {
        return longObjectValue;
    }

    public void setLongObjectValue(Long longObjectValue) {
        this.longObjectValue = longObjectValue;
    }

    public Float getFloatObjectValue() {
        return floatObjectValue;
    }

    public void setFloatObjectValue(Float floatObjectValue) {
        this.floatObjectValue = floatObjectValue;
    }

    public Double getDoubleObjectValue() {
        return doubleObjectValue;
    }

    public void setDoubleObjectValue(Double doubleObjectValue) {
        this.doubleObjectValue = doubleObjectValue;
    }

    public Boolean getBooleanObjectValue() {
        return booleanObjectValue;
    }

    public void setBooleanObjectValue(Boolean booleanObjectValue) {
        this.booleanObjectValue = booleanObjectValue;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public ObjectModel getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(ObjectModel objectModel) {
        this.objectModel = objectModel;
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }

    public void setJsonElement(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public List<Float> getFloatList() {
        return floatList;
    }

    public void setFloatList(List<Float> floatList) {
        this.floatList = floatList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<ObjectModel> getObjectModelList() {
        return objectModelList;
    }

    public void setObjectModelList(List<ObjectModel> objectModelList) {
        this.objectModelList = objectModelList;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray(float[] floatArray) {
        this.floatArray = floatArray;
    }

    public Integer[] getIntegerObjectArray() {
        return integerObjectArray;
    }

    public void setIntegerObjectArray(Integer[] integerObjectArray) {
        this.integerObjectArray = integerObjectArray;
    }

    public Float[] getFloatObjectArray() {
        return floatObjectArray;
    }

    public void setFloatObjectArray(Float[] floatObjectArray) {
        this.floatObjectArray = floatObjectArray;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public List<Double> getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(List<Double> doubleList) {
        this.doubleList = doubleList;
    }

    public List<JsonObject> getJsonObjectList() {
        return jsonObjectList;
    }

    public void setJsonObjectList(List<JsonObject> jsonObjectList) {
        this.jsonObjectList = jsonObjectList;
    }

    public long[] getLongArray() {
        return longArray;
    }

    public void setLongArray(long[] longArray) {
        this.longArray = longArray;
    }

    public double[] getDoubleArray() {
        return doubleArray;
    }

    public void setDoubleArray(double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    public Long[] getLongObjectArray() {
        return longObjectArray;
    }

    public void setLongObjectArray(Long[] longObjectArray) {
        this.longObjectArray = longObjectArray;
    }

    public Double[] getDoubleObjectArray() {
        return doubleObjectArray;
    }

    public void setDoubleObjectArray(Double[] doubleObjectArray) {
        this.doubleObjectArray = doubleObjectArray;
    }

    public Boolean[] getBooleanObjectArray() {
        return booleanObjectArray;
    }

    public void setBooleanObjectArray(Boolean[] booleanObjectArray) {
        this.booleanObjectArray = booleanObjectArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public ObjectModel[] getObjectModelArray() {
        return objectModelArray;
    }

    public void setObjectModelArray(ObjectModel[] objectModelArray) {
        this.objectModelArray = objectModelArray;
    }

    public JsonObject[] getJsonObjectArray() {
        return jsonObjectArray;
    }

    public void setJsonObjectArray(JsonObject[] jsonObjectArray) {
        this.jsonObjectArray = jsonObjectArray;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public void setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

    public JsonArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public StaticNestedClass getStaticNestedClass() {
        return staticNestedClass;
    }

    public void setStaticNestedClass(StaticNestedClass staticNestedClass) {
        this.staticNestedClass = staticNestedClass;
    }

    public List<StaticNestedClass> getStaticNestedClassList() {
        return staticNestedClassList;
    }

    public void setStaticNestedClassList(List<StaticNestedClass> staticNestedClassList) {
        this.staticNestedClassList = staticNestedClassList;
    }

    public StaticNestedClass[] getStaticNestedClassArray() {
        return staticNestedClassArray;
    }

    public void setStaticNestedClassArray(StaticNestedClass[] staticNestedClassArray) {
        this.staticNestedClassArray = staticNestedClassArray;
    }

    public List<String>[] getListStringArray() {
        return listStringArray;
    }

    public void setListStringArray(List<String>[] listStringArray) {
        this.listStringArray = listStringArray;
    }

    public List<List<String>>[] getListListStringArray() {
        return listListStringArray;
    }

    public void setListListStringArray(List<List<String>>[] listListStringArray) {
        this.listListStringArray = listListStringArray;
    }

    public Map<String, List<ObjectModel>>[] getMapStringArrayModelArray() {
        return mapStringArrayModelArray;
    }

    public void setMapStringArrayModelArray(Map<String, List<ObjectModel>>[] mapStringArrayModelArray) {
        this.mapStringArrayModelArray = mapStringArrayModelArray;
    }
}
