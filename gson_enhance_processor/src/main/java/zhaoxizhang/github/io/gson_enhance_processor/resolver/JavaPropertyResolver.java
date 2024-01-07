package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public class JavaPropertyResolver {
    private static final String TAG = "JavaPropertySolver";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final Element element;

    public JavaPropertyResolver(ProcessingEnvironment processingEnv, MessageLogger logger, Element element) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.element = element;
    }

    public JavaField resolveJavaField() {
        JavaField javaField = new JavaField();
        javaField.setFieldName(element.getSimpleName().toString());
        javaField.setTransient(element.getModifiers().contains(Modifier.TRANSIENT));
        javaField.setFinal(element.getModifiers().contains(Modifier.FINAL));
        SerializedName serializedName = element.getAnnotation(SerializedName.class);
        if (serializedName != null) {
            List<String> keys = new ArrayList<>();
            keys.add(serializedName.value());
            keys.addAll(Arrays.asList(serializedName.alternate()));
            javaField.setSerializedNames(keys);
        }
        javaField.setJavaType(resolveJavaType());
        return javaField;
    }

    public JavaType resolveJavaType() {
        JavaTypeResolver javaTypeResolver = new JavaTypeResolver(processingEnv, logger, element);
        return javaTypeResolver.resolveJavaType();
    }
}
