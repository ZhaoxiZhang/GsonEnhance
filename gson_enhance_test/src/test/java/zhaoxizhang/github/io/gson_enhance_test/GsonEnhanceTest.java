package zhaoxizhang.github.io.gson_enhance_test;

import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import zhaoxizhang.github.io.gson_enhance_annotation.GsonNewInstance;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapter;
import zhaoxizhang.github.io.gsonenhance.GsonTypeAdapterFactory;

public class GsonEnhanceTest {
    private final Gson gson = new Gson();
    private final Gson gsonEnhance = (new GsonBuilder()).registerTypeAdapterFactory(new GsonTypeAdapterFactory()).create();
    private final String TEST_STRING_VALUE = "hello world";
    private final float TEST_FLOAT_VALUE = 1f;
    private final double TEST_DOUBLE_VALUE = 1.0;
    private final long TEST_LONG_VALUE = 1L;

    @Test
    public void testInt() {
        TestModel testModel = new TestModel();
        testModel.setIntValue(1);
        String toJsonString = gson.toJson(testModel);

        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);
        Assert.assertEquals(fromJsonTestModel.getIntValue(), 1);
        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    @Test
    public void testInteger() {
        TestModel testModel = new TestModel();
        testModel.setIntegerValue(1);
        String toJsonString = gson.toJson(testModel);

        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);
        Assert.assertEquals(fromJsonTestModel.getIntegerValue().intValue(), 1);
        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    @Test
    public void testString() {
        TestModel testModel = new TestModel();
        testModel.setStringValue(TEST_STRING_VALUE);
        String toJsonString = gson.toJson(testModel);

        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);
        Assert.assertEquals(fromJsonTestModel.getStringValue(), TEST_STRING_VALUE);
        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    @Test
    public void testMap() {
        TestModel testModel = new TestModel();

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put(TEST_STRING_VALUE, TEST_STRING_VALUE);

        Map<String, List<String>> stringListMap = new HashMap<>();
        List<String> stringList = new ArrayList<String>() {{
            add(TEST_STRING_VALUE);
        }};
        stringListMap.put(TEST_STRING_VALUE, stringList);

        Map<String, TestObjectModel> stringTestObjectModelMap = new HashMap<>();
        TestObjectModel testObjectModel = buildTestObjectModel();
        stringTestObjectModelMap.put(TEST_STRING_VALUE, testObjectModel);

        testModel.setStringStringMap(stringStringMap);
        testModel.setStringListMap(stringListMap);
        testModel.setStringTestObjectModelMap(stringTestObjectModelMap);

        String toJsonString = gson.toJson(testModel);

        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);

        Assert.assertEquals(fromJsonTestModel.getStringStringMap().get(TEST_STRING_VALUE), TEST_STRING_VALUE);
        Truth.assertThat(fromJsonTestModel.getStringListMap().get(TEST_STRING_VALUE)).contains(TEST_STRING_VALUE);
        Truth.assertThat(fromJsonTestModel.getStringTestObjectModelMap().get(TEST_STRING_VALUE)).isEqualTo(testObjectModel);

        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    @Test
    public void testArray() {
        TestModel testModel = new TestModel();
        int[] intArray = {1, 2, 3};
        Integer[] integerArray = {4, 5, 6};
        String[] stringArray = {TEST_STRING_VALUE, TEST_STRING_VALUE, TEST_STRING_VALUE};
        TestObjectModel[] testObjectModelArray = {buildTestObjectModel(), buildTestObjectModel(), buildTestObjectModel()};

        testModel.setIntArray(intArray);
        testModel.setIntegerArray(integerArray);
        testModel.setStringArray(stringArray);
        testModel.setTestObjectModelArray(testObjectModelArray);

        String toJsonString = gson.toJson(testModel);
        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);

        Truth.assertThat(fromJsonTestModel.getIntArray()).asList().containsAtLeast(1, 2, 3);
        Truth.assertThat(fromJsonTestModel.getIntegerArray()).asList().containsAtLeast(4, 5, 6);
        Truth.assertThat(fromJsonTestModel.getStringArray()).asList().contains(TEST_STRING_VALUE);
        Truth.assertThat(fromJsonTestModel.getTestObjectModelArray()).asList().contains(buildTestObjectModel());
        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    @Test
    public void testObjectModel() {
        TestObjectModel testObjectModel = buildTestObjectModel();
        String toJsonString = gson.toJson(testObjectModel);

        TestObjectModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestObjectModel.class);
        Assert.assertEquals(fromJsonTestModel.getIntValue(), 1);
        Assert.assertEquals(fromJsonTestModel.getIntegerValue().intValue(), 1);
        Assert.assertEquals(fromJsonTestModel.getStringValue(), TEST_STRING_VALUE);

        Assert.assertEquals(gsonEnhance.toJson(testObjectModel), toJsonString);
    }

    @Test
    public void testSerializeName() {
        String toJsonString = "{\"alternate_intValue\": 1}";

        TestModel testModel = gsonEnhance.fromJson(toJsonString, TestModel.class);
        Assert.assertEquals(testModel.getIntValue(), 1);
    }

    @Test
    public void testStaticNestedClass() {
        TestObjectModel testObjectModel = buildTestObjectModel();
        String toJsonString = gson.toJson(testObjectModel);

        TestObjectModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestObjectModel.class);
        Assert.assertEquals(fromJsonTestModel.getIntValue(), 1);
        Assert.assertEquals(fromJsonTestModel.getIntegerValue().intValue(), 1);
        Assert.assertEquals(fromJsonTestModel.getStringValue(), TEST_STRING_VALUE);

        Assert.assertEquals(gsonEnhance.toJson(testObjectModel), toJsonString);
    }

    @Test
    public void testInheritanceClass() {
        TestInheritanceModel inheritanceModel = new TestInheritanceModel();
        inheritanceModel.setFloatProValue(TEST_FLOAT_VALUE);
        inheritanceModel.setLongProValue(TEST_LONG_VALUE);
        inheritanceModel.setDoublePubValue(TEST_DOUBLE_VALUE);
        inheritanceModel.setStringValue(TEST_STRING_VALUE);

        String toJsonString = gson.toJson(inheritanceModel);

        TestInheritanceModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestInheritanceModel.class);
        Assert.assertEquals(fromJsonTestModel.getFloatProValue(), TEST_FLOAT_VALUE, 0);
        Assert.assertEquals(fromJsonTestModel.getDoublePubValue(), TEST_DOUBLE_VALUE, 0);
        Assert.assertEquals(fromJsonTestModel.getStringValue(), TEST_STRING_VALUE);

        Assert.assertEquals(gsonEnhance.toJson(inheritanceModel), toJsonString);
    }

    @Test
    public void testTransientField() {
        TestModel testModel = new TestModel();
        testModel.setIntTransientValue(1);

        String toJsonString = gson.toJson(testModel);

        TestModel fromJsonTestModel = gsonEnhance.fromJson(toJsonString, TestModel.class);

        Assert.assertEquals(fromJsonTestModel.getIntTransientValue(), 0);
        Assert.assertEquals(gsonEnhance.toJson(testModel), toJsonString);
    }

    private TestObjectModel buildTestObjectModel() {
        TestObjectModel testObjectModel = new TestObjectModel();
        testObjectModel.setIntValue(1);
        testObjectModel.setIntegerValue(1);
        testObjectModel.setStringValue(TEST_STRING_VALUE);
        return testObjectModel;
    }

    @GsonNewInstance
    @GsonTypeAdapter
    public static class TestModel {
        @SerializedName(value = "intValue", alternate = {"alternate_intValue"})
        private int intValue;
        private Integer integerValue;
        private String stringValue;
        private TestObjectModel testObjectModel;
        private Map<String, String> stringStringMap;
        private Map<String, List<String>> stringListMap;
        private Map<String, TestObjectModel> stringTestObjectModelMap;
        private int[] intArray;
        private Integer[] integerArray;
        private String[] stringArray;
        private TestObjectModel[] testObjectModelArray;
        private transient int intTransientValue;
        protected float floatProValue;
        protected long longProValue;
        public double doublePubValue;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public void setIntegerValue(Integer integerValue) {
            this.integerValue = integerValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String string) {
            this.stringValue = string;
        }

        public TestObjectModel getTestObjectModel() {
            return testObjectModel;
        }

        public void setTestObjectModel(TestObjectModel testObjectModel) {
            this.testObjectModel = testObjectModel;
        }

        public Map<String, String> getStringStringMap() {
            return stringStringMap;
        }

        public void setStringStringMap(Map<String, String> stringStringMap) {
            this.stringStringMap = stringStringMap;
        }

        public Map<String, List<String>> getStringListMap() {
            return stringListMap;
        }

        public void setStringListMap(Map<String, List<String>> stringListMap) {
            this.stringListMap = stringListMap;
        }

        public Map<String, TestObjectModel> getStringTestObjectModelMap() {
            return stringTestObjectModelMap;
        }

        public void setStringTestObjectModelMap(Map<String, TestObjectModel> stringTestObjectModelMap) {
            this.stringTestObjectModelMap = stringTestObjectModelMap;
        }

        public float getFloatProValue() {
            return floatProValue;
        }

        public void setFloatProValue(float floatProValue) {
            this.floatProValue = floatProValue;
        }

        public long getLongProValue() {
            return longProValue;
        }

        public void setLongProValue(long longProValue) {
            this.longProValue = longProValue;
        }

        public double getDoublePubValue() {
            return doublePubValue;
        }

        public void setDoublePubValue(double doublePubValue) {
            this.doublePubValue = doublePubValue;
        }

        public int[] getIntArray() {
            return intArray;
        }

        public void setIntArray(int[] intArray) {
            this.intArray = intArray;
        }

        public Integer[] getIntegerArray() {
            return integerArray;
        }

        public void setIntegerArray(Integer[] integerArray) {
            this.integerArray = integerArray;
        }

        public String[] getStringArray() {
            return stringArray;
        }

        public void setStringArray(String[] stringArray) {
            this.stringArray = stringArray;
        }

        public TestObjectModel[] getTestObjectModelArray() {
            return testObjectModelArray;
        }

        public void setTestObjectModelArray(TestObjectModel[] testObjectModelArray) {
            this.testObjectModelArray = testObjectModelArray;
        }

        public int getIntTransientValue() {
            return intTransientValue;
        }

        public void setIntTransientValue(int intTransientValue) {
            this.intTransientValue = intTransientValue;
        }
    }

    @GsonTypeAdapter
    public static class TestInheritanceModel extends TestModel {
        private String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

    @GsonTypeAdapter
    public static class TestObjectModel {
        private int intValue;
        private Integer integerValue;
        private String stringValue;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public void setIntegerValue(Integer integerValue) {
            this.integerValue = integerValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestObjectModel)) return false;
            TestObjectModel that = (TestObjectModel) o;
            return intValue == that.intValue && Objects.equals(integerValue, that.integerValue) && Objects.equals(stringValue, that.stringValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue, integerValue, stringValue);
        }
    }
}