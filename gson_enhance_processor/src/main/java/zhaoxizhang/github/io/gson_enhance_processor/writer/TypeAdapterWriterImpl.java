package zhaoxizhang.github.io.gson_enhance_processor.writer;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.ResolverUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2023/12/11
 */
public class TypeAdapterWriterImpl extends AbstractTypeAdapterWriter {
    private static final String TAG = "TypeAdapterAutoWriterImpl";

    public TypeAdapterWriterImpl(ProcessingEnvironment processingEnv, MessageLogger logger, JavaField javaField) {
        super(processingEnv, logger, javaField);
    }

    /**
     * jsonWriter.name("objectModel");
     * getObjectModelTypeAdapter().write(jsonWriter, value.getObjectModel());
     */
    @Override
    public void doGen(@Nonnull CodeBlock.Builder codeBlockBuilder, @Nonnull String deserializeName) {
        JavaType javaType = javaField.getJavaType();
        TypeName typeName = ResolverUtils.boxPrimitive(javaType.getTypeName());
        codeBlockBuilder.addStatement("$L.name($S)", Constant.Naming.JSON_WRITER, deserializeName);
        codeBlockBuilder.addStatement("$L().write($L, $L.$L())", ResolverUtils.genTypeAdapterMethodName(typeName, logger), Constant.Naming.JSON_WRITER, Constant.TypeAdapterWriterConstant.WRITER_VALUE_KEY, getGettingFunction(javaField));
    }

    @Override
    public String getTag() {
        return TAG;
    }
}
