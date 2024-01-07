package zhaoxizhang.github.io.gson_enhance_processor.vo;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public class JavaType {
    private final Element element;
    private final TypeName typeName;
    private final TypeMirror typeMirror;
    private String rawType;
    /**
     * ArrayType 使用
     */
    private TypeMirror componentType;
    /**
     * Collection 的泛型类型
     */
    private List<? extends TypeMirror> generics;

    public JavaType(@Nonnull Element element) {
        this.element = element;
        this.typeMirror = element.asType();
        this.typeName = ClassName.get(typeMirror);
    }

    public String getRawType() {
        return rawType;
    }

    public void setRawType(String rawType) {
        this.rawType = rawType;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public TypeMirror getComponentType() {
        return componentType;
    }

    public void setComponentType(TypeMirror componentType) {
        this.componentType = componentType;
    }

    public List<? extends TypeMirror> getGenerics() {
        return generics;
    }

    public void setGenerics(List<? extends TypeMirror> generics) {
        this.generics = generics;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "JavaType{" +
                "rawType='" + rawType + '\'' +
                ", generics=" + generics +
                '}';
    }
}
