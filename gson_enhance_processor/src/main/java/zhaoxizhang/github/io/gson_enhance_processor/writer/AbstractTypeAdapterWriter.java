package zhaoxizhang.github.io.gson_enhance_processor.writer;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.AbstractTypeAdapter;
import zhaoxizhang.github.io.gson_enhance_processor.utils.CollectionUtils;
import zhaoxizhang.github.io.gson_enhance_processor.utils.StringUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2023/12/11
 */
public abstract class AbstractTypeAdapterWriter extends AbstractTypeAdapter {


    public AbstractTypeAdapterWriter(ProcessingEnvironment processingEnv, MessageLogger logger, JavaField javaField) {
        super(processingEnv, logger, javaField);
    }

    public abstract void doGen(CodeBlock.Builder codeBlockBuilder, String deserializeName);

    public CodeBlock gen() {
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        String deserializeName = CollectionUtils.isEmpty(javaField.getSerializedNames()) ? javaField.getFieldName() : (javaField.getSerializedNames().get(0));
        doGen(codeBlockBuilder, deserializeName);
        return codeBlockBuilder.build();
    }

    protected String getGettingFunction(@Nonnull JavaField javaField) {
        String fieldName = javaField.getFieldName();
        if (StringUtils.isEmpty(fieldName)) {
            throw new IllegalArgumentException("fieldName is empty or null");
        }
        String prefix;
        TypeName javaFieldTypeName = TypeName.get(javaField.getJavaType().getTypeMirror());
        if (TypeName.BOOLEAN == javaFieldTypeName) {
            if (fieldName.startsWith(IS_PREFIX)) {
                if (fieldName.length() > IS_PREFIX.length() && fieldName.codePointAt(IS_PREFIX.length()) >= 97 && fieldName.codePointAt(IS_PREFIX.length()) <= 122) {
                    return IS_PREFIX + StringUtils.firstUpperCase(fieldName);
                }
                return IS_PREFIX +
                        (fieldName.length() > IS_PREFIX.length() ? fieldName.substring(IS_PREFIX.length()) : StringUtils.firstUpperCase(IS_PREFIX));
            } else {
                prefix = IS_PREFIX;
            }
        } else {
            prefix = "get";
        }
        if (fieldName.length() > 1) {
            // 第一个字符小写字母，第二个字符大写字母，如 aA 那么对应的 settingFunction 是 setaA()
            if (fieldName.codePointAt(0) >= ASCII_LOWER_A && fieldName.codePointAt(0) <= ASCII_LOWER_Z
                    && fieldName.codePointAt(1) >= ASCII_UPPER_A && fieldName.codePointAt(1) <= ASCII_UPPER_Z) {
                return prefix + fieldName;
            }
        }
        return prefix +
                (fieldName.length() == 1 ? fieldName.toUpperCase() : fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
    }
}
