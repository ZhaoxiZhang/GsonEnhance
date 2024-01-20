
## Overview

Gson 在序列化/反序列化过程，对于非内建类型，通过反射的手段来收集类对象的所有字段，额外的在反序列化过程通过反射来创建类实例，因此导致了序列化/反序列化过程的耗时性能问题。当然，Gson 也提供了一些接口让我们重写以规避反射：
- TypeAdapter：自定义对象的序列化/反序列化过程
- InstanceCreator：自定义创建对象实例
  GsonEnhance 利用上述特性，通过 APT 技术在编译时自动生成类对象相关的 TypeAdapter。目前提供了两个注解`GsonTypeAdapter`和`GsonNewInstance`：
- `GsonTypeAdapter`：能够生成完整的 TypeAdpater 代码，对于使用了 `GsonTypeAdapter` 注解的类，无需再使用 `GsonNewInstance` 注解。
- `GsonNewInstance`：自定义类对象创建，避免反射创建类实例。在移动端对于包大小有要求的情况下，TypeAdapter 生成的相关代码可能较多，因此退而求其次，减少反射创建类实例带来的性能影响。

## Usage

### GsonTypeAdapter

添加`GsonTypeAdapter`注解：
```Java
@GsonTypeAdapter  
public class GsonEnhanceModel {  
	@SerializedName(value = "int_value", alternate = {"alternate_int_value", "int_value_alternate"})  
	private int intValue;  
	private long longValue;  
	private float floatValue;

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
}
```

编译后会在同包名目录下生成关联的 TypeAdapter，以及配套的 GsonTypeAdapterFactory
```Java
public class GsonEnhanceModelTypeAdapter extends TypeAdapter<GsonEnhanceModel> {
  private Gson gson;

  private TypeAdapter<Integer> integerTypeAdapter;

  private TypeAdapter<Long> longTypeAdapter;

  private TypeAdapter<Float> floatTypeAdapter;

  public GsonEnhanceModelTypeAdapter(Gson gson) {
    this.gson = gson;
  }

  @Override
  public GsonEnhanceModel read(JsonReader jsonReader) throws IOException {
    GsonEnhanceModel deserializeValue = new GsonEnhanceModel();
    jsonReader.beginObject();
    while (jsonReader.hasNext()) {
      String jsonName = jsonReader.nextName();
      switch (jsonName) {
        case "int_value":
        case "alternate_int_value":
        case "int_value_alternate":
          int intValueInstance = getIntegerTypeAdapter().read(jsonReader);
          deserializeValue.setIntValue(intValueInstance);
          break;
        case "longValue":
          long longValueInstance = getLongTypeAdapter().read(jsonReader);
          deserializeValue.setLongValue(longValueInstance);
          break;
        case "floatValue":
          float floatValueInstance = getFloatTypeAdapter().read(jsonReader);
          deserializeValue.setFloatValue(floatValueInstance);
          break;
        default:
          jsonReader.skipValue();
          break;
      }
    }
    jsonReader.endObject();
    return deserializeValue;
  }

  @Override
  public void write(JsonWriter jsonWriter, GsonEnhanceModel value) throws IOException {
    if (value == null) {
      jsonWriter.nullValue();
      return;
    }
    jsonWriter.beginObject();
    jsonWriter.name("int_value");
    getIntegerTypeAdapter().write(jsonWriter, value.getIntValue());
    jsonWriter.name("longValue");
    getLongTypeAdapter().write(jsonWriter, value.getLongValue());
    jsonWriter.name("floatValue");
    getFloatTypeAdapter().write(jsonWriter, value.getFloatValue());
    jsonWriter.endObject();
  }

  public TypeAdapter<Integer> getIntegerTypeAdapter() {
    if(integerTypeAdapter == null) {
      integerTypeAdapter = (TypeAdapter<Integer>)gson.getAdapter(TypeToken.get(Integer.class));
    }
    return integerTypeAdapter;
  }

  public TypeAdapter<Long> getLongTypeAdapter() {
    if(longTypeAdapter == null) {
      longTypeAdapter = (TypeAdapter<Long>)gson.getAdapter(TypeToken.get(Long.class));
    }
    return longTypeAdapter;
  }

  public TypeAdapter<Float> getFloatTypeAdapter() {
    if(floatTypeAdapter == null) {
      floatTypeAdapter = (TypeAdapter<Float>)gson.getAdapter(TypeToken.get(Float.class));
    }
    return floatTypeAdapter;
  }
}
```

APT 生成关联的 `TypeAdapterFactory`, 名字固定为：`GsonTypeAdapterFactory`
```Java
public class GsonTypeAdapterFactory implements TypeAdapterFactory {
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<? super T> clazz = type.getRawType();
    if (clazz == GsonEnhanceModel.class) {
      return (TypeAdapter<T>) new GsonEnhanceModelTypeAdapter(gson);
    }
    return null;
  }
}
```

使用时通过 `GsonBuilder` 创建 `Gson` 对象：
```Java
Gson gsonEnhance = (new GsonBuilder()).registerTypeAdapterFactory(new GsonTypeAdapterFactory()).create();
```

### GsonNewInstance

添加`GsonNewInstance`注解
```Java
@GsonNewInstance
public class GsonEnhanceModel {
    ...
}
```

编译后生成一个 `registerInstanceCreator` 方法，为每一个添加了注解的类生成对应的 `createInstance` 来避免对象实例化时的反射
```Java
public class GsonEnhance {
  public static void registerInstanceCreator(GsonBuilder gsonBuilder) {
    gsonBuilder.registerTypeAdapter(ObjectModel.class, new InstanceCreator<ObjectModel>() {
      @Override
      public ObjectModel createInstance(Type type) {
        return new ObjectModel();
      }
    });
  }
}
```

通过 `GsonBuilder` 注入 `registerInstanceCreator` 方法
```Java
GsonBuilder gsonBuilder = new GsonBuilder();
GsonEnhance.registerInstanceCreator(gsonbuilder);
Gson gson = gsonBuilder.create();
```