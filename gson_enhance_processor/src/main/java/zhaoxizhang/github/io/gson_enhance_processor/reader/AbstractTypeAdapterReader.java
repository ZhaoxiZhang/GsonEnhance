package zhaoxizhang.github.io.gson_enhance_processor.reader;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import java.util.List;

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
public abstract class AbstractTypeAdapterReader extends AbstractTypeAdapter {

    public AbstractTypeAdapterReader(ProcessingEnvironment processingEnv, MessageLogger logger, JavaField javaField) {
        super(processingEnv, logger, javaField);
    }

    public abstract void doGen(CodeBlock.Builder codeBlockBuilder);

    public CodeBlock gen() {
        logger.debug(getTag(), "gen start: javaField = " + javaField);
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add(getCaseStatement());
        codeBlockBuilder.indent();
        doGen(codeBlockBuilder);
        codeBlockBuilder.addStatement("break");
        codeBlockBuilder.unindent();
        return codeBlockBuilder.build();
    }

    protected String getCaseStatement() {
        List<String> serializedNames = javaField.getSerializedNames();
        if (CollectionUtils.isEmpty(serializedNames)) {
            return String.format("case \"%s\":\n", javaField.getFieldName());
        } else {
            StringBuilder caseStatementBuilder = new StringBuilder();
            for (String serializedName : serializedNames) {
                caseStatementBuilder.append(String.format("case \"%s\":\n", serializedName));
            }
            return caseStatementBuilder.toString();
        }
    }

    @Nonnull
    protected String getSettingFunction(@Nonnull JavaField javaField) {
        String fieldName = javaField.getFieldName();
        if (StringUtils.isEmpty(fieldName)) {
            throw new IllegalArgumentException("fieldName is empty or null");
        }
        TypeName javaFieldTypeName = TypeName.get(javaField.getJavaType().getTypeMirror());
        if (TypeName.BOOLEAN == javaFieldTypeName) {
            if (fieldName.startsWith(IS_PREFIX)) {
                if (fieldName.length() > IS_PREFIX.length() && fieldName.codePointAt(IS_PREFIX.length()) >= ASCII_LOWER_A && fieldName.codePointAt(IS_PREFIX.length()) <= ASCII_LOWER_Z) {
                    return "set" + StringUtils.firstUpperCase(fieldName);
                }
                return "set" +
                        (fieldName.length() > IS_PREFIX.length() ? fieldName.substring(IS_PREFIX.length()) : StringUtils.firstUpperCase(IS_PREFIX));
            }
        }
        if (fieldName.length() > 1) {
            // 第一个字符小写字母，第二个字符大写字母，如 aA 那么对应的 settingFunction 是 setaA()
            if (fieldName.codePointAt(0) >= ASCII_LOWER_A && fieldName.codePointAt(0) <= ASCII_LOWER_Z
                    && fieldName.codePointAt(1) >= 65 && fieldName.codePointAt(1) <= 90) {
                return "set" + fieldName;
            }
        }
        return "set" + StringUtils.firstUpperCase(fieldName);
    }
}
