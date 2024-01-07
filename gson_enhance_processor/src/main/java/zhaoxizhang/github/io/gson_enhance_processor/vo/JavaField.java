package zhaoxizhang.github.io.gson_enhance_processor.vo;

import java.util.List;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/25
 */
public class JavaField {
    private String fieldName;
    private List<String> serializedNames;
    private boolean isTransient;
    private boolean isFinal;
    private JavaType javaType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getSerializedNames() {
        return serializedNames;
    }

    public void setSerializedNames(List<String> serializedNames) {
        this.serializedNames = serializedNames;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean aTransient) {
        isTransient = aTransient;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
    }

    @Override
    public String toString() {
        return "JavaField{" +
                "fieldName='" + fieldName + '\'' +
                ", serializedNames=" + serializedNames +
                ", isTransient=" + isTransient +
                ", isFinal=" + isFinal +
                ", javaType=" + javaType +
                '}';
    }
}
