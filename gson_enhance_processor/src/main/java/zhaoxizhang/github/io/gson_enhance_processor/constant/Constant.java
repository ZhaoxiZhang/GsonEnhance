package zhaoxizhang.github.io.gson_enhance_processor.constant;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public interface Constant {

    interface Naming {
        String PACKAGE_NAME = "zhaoxizhang.github.io.gsonenhance";
        String JSON_READER = "jsonReader";
        String JSON_WRITER = "jsonWriter";
        String TYPE_ADAPTER = "TypeAdapter";
        String INSTANCE_NAME = "instance";
        String CLAZZ_NAME = "clazz";
        String GSON_TYPE_ADAPTER_FACTORY = "GsonTypeAdapterFactory";
        String GSON_ENHANCE_CLASS_NAME = "GsonEnhance";
        String GSON_BUILDER_NAME = "gsonBuilder";
    }

    interface TypeAdapterReaderConstant {
        String DESERIALIZED_VALUE_KEY = "deserializeValue";
    }

    interface TypeAdapterWriterConstant {
        String WRITER_VALUE_KEY = "value";
    }
}
