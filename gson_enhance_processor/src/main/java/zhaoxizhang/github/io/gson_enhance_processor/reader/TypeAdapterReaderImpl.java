package zhaoxizhang.github.io.gson_enhance_processor.reader;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.ResolverUtils;
import zhaoxizhang.github.io.gson_enhance_processor.utils.StringUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2023/12/11
 */
public class TypeAdapterReaderImpl extends AbstractTypeAdapterReader {
    private static final String TAG = "TypeAdapterAutoReaderImpl";

    public TypeAdapterReaderImpl(ProcessingEnvironment processingEnv, MessageLogger logger, JavaField javaField) {
        super(processingEnv, logger, javaField);
    }

    /**
     * ObjectModel objectModelInstance = getObjectModelTypeAdapter().read(jsonReader);
     * deserializeValue.setObjectModel(objectModelInstance);
     */
    @Override
    public void doGen(@Nonnull CodeBlock.Builder codeBlockBuilder) {
        JavaType javaType = javaField.getJavaType();
        TypeName typeName = ResolverUtils.boxPrimitive(javaType.getTypeName());
        String instanceFieldName = javaField.getFieldName() + StringUtils.firstUpperCase(Constant.Naming.INSTANCE_NAME);
        codeBlockBuilder.addStatement("$T $L = $L().read($L)", javaType.getTypeMirror(), instanceFieldName, ResolverUtils.genTypeAdapterMethodName(typeName, logger), Constant.Naming.JSON_READER);
        codeBlockBuilder.addStatement("$L.$L($L)", Constant.TypeAdapterReaderConstant.DESERIALIZED_VALUE_KEY,
                getSettingFunction(javaField),
                instanceFieldName);
    }

    @Override
    public String getTag() {
        return TAG;
    }
}
