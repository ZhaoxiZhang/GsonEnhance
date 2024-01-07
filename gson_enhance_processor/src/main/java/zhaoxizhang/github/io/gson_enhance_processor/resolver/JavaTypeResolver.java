package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public class JavaTypeResolver {
    private static final String TAG = "JavaTypeResolver";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final Element element;
    private final TypeMirror typeMirror;

    public JavaTypeResolver(ProcessingEnvironment processingEnv, MessageLogger logger, Element element) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.element = element;
        this.typeMirror = element.asType();
    }

    public JavaType resolveJavaType() {
        JavaType javaType = new JavaType(element);

        // int java.lang.String
        javaType.setRawType(typeMirror.toString());
        if (typeMirror instanceof DeclaredType) {
            javaType.setGenerics(((DeclaredType) typeMirror).getTypeArguments());
        } else if (typeMirror instanceof ArrayType) {
            javaType.setComponentType(((ArrayType) typeMirror).getComponentType());
        }

        return javaType;
    }
}
