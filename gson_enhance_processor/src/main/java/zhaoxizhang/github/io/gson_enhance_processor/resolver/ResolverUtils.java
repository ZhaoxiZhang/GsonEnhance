package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.WildcardTypeName;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;
import zhaoxizhang.github.io.gson_enhance_processor.utils.StringUtils;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/27
 */
public class ResolverUtils {
    private static final String TAG = "ResolverUtils";

    @Nonnull
    public static String simpleName(ClassName className, MessageLogger logger) {
        String resultSimpleName;
        if (className.enclosingClassName() == null) {
            resultSimpleName = className.simpleName();
        } else {
            List<String> simpleNames = className.simpleNames();
            logger.debug(TAG, "has enclosingClassName: " + simpleNames);
            StringBuilder stringBuilder = new StringBuilder();
            for (String simpleName : simpleNames) {
                stringBuilder.append(stringBuilder.length() == 0 ? simpleName : "$" + simpleName);
            }
            resultSimpleName = stringBuilder.toString();
        }
        logger.debug(TAG, "simpleName = " + resultSimpleName);
        return resultSimpleName;
    }

    public static TypeName boxPrimitive(@Nonnull TypeName typeName) {
        if (typeName.isPrimitive()) {
            return typeName.box();
        }
        return typeName;
    }

    @Nonnull
    public static String genTypeAdapterMethodName(@Nonnull TypeName typeName, @Nonnull MessageLogger logger) {
        return "get" + StringUtils.firstUpperCase(genTypeAdapterFieldNameWithSuffix(typeName, logger));
    }

    @Nonnull
    public static String genTypeAdapterFieldNameWithSuffixInCamel(@Nonnull TypeName typeName, @Nonnull MessageLogger logger) {
        TypeName handleTypeName = boxPrimitive(typeName);
        return StringUtils.firstLowerCase(genTypeAdapterFieldNameWithSuffix(handleTypeName, logger));
    }

    @Nonnull
    public static String genTypeAdapterFieldNameWithSuffix(@Nonnull TypeName typeName, @Nonnull MessageLogger logger) {
        return genTypeAdapterFieldName(typeName, logger) + Constant.Naming.TYPE_ADAPTER;
    }

    public static String genTypeAdapterFieldName(@Nonnull TypeName typeName, @Nonnull MessageLogger logger) {
        TypeName handleTypeName = boxPrimitive(typeName);
        String typeAdapterFieldName = genTypeAdapterFieldNameHandler(handleTypeName, logger);
        if (typeName instanceof ArrayTypeName) {
            if (((ArrayTypeName) typeName).componentType.isPrimitive()) {
                return typeAdapterFieldName + "UnboxArray";
            }
            return typeAdapterFieldName + "Array";
        }
        return typeAdapterFieldName;
    }

    public static String genTypeAdapterFieldNameHandler(@Nonnull TypeName typeName, @Nonnull MessageLogger logger) {
        logger.debug(TAG, "genTypeAdapterFieldName: typeName = " + typeName);
        if (typeName instanceof ClassName) {
            return ((ClassName) typeName).simpleName();
        } else if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            String fieldName = parameterizedTypeName.rawType.simpleName()
                    + parameterizedTypeName.typeArguments.stream().map(new Function<TypeName, String>() {
                @Override
                public String apply(TypeName typeName) {
                    return genTypeAdapterFieldNameHandler(typeName, logger);
                }
            }).collect(Collectors.joining());

            logger.debug(TAG, "genTypeAdapterFieldName: fieldName = " + fieldName);
            return fieldName;
        } else if (typeName instanceof ArrayTypeName) {
            return genTypeAdapterFieldNameHandler(((ArrayTypeName) typeName).componentType, logger);
        } else if (typeName instanceof WildcardTypeName) {
            // TODO
        }
        return typeName.toString();
    }
}
