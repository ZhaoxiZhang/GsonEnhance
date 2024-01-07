package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;
import zhaoxizhang.github.io.gson_enhance_processor.utils.ElementUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/6
 */
public class TypeAdapterFactoryGeneratorImpl {
    private static final String TAG = "TypeAdapterFactoryGeneratorImpl";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final List<ClassPropertySolver> classPropertySolverList;

    interface ParameterName {
        String METHOD_CREATE_GSON = "gson";
    }

    public TypeAdapterFactoryGeneratorImpl(ProcessingEnvironment processingEnv, MessageLogger logger, List<ClassPropertySolver> classPropertySolverList) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.classPropertySolverList = classPropertySolverList;
    }

    public void gen() {
        logger.debug(TAG, "gen start");

        TypeVariableName genericType = TypeVariableName.get("T");
        MethodSpec createMethodSpec = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(genericType)
                .returns(ParameterizedTypeName.get(ClassName.get(TypeAdapter.class), genericType))
                .addAnnotation(Override.class)
                .addParameter(Gson.class, ParameterName.METHOD_CREATE_GSON)
                .addParameter(ParameterizedTypeName.get(ClassName.get(TypeToken.class), genericType), "type")
                .addCode(getCreateCodeBlock())
                .build();

        TypeSpec classTypeSpec = TypeSpec.classBuilder(Constant.Naming.GSON_TYPE_ADAPTER_FACTORY)
                .addSuperinterface(TypeAdapterFactory.class)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createMethodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(Constant.Naming.PACKAGE_NAME, classTypeSpec)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            logger.error(TAG, e.getMessage());
        }
    }

    private CodeBlock getCreateCodeBlock() {
        logger.debug(TAG, "getCreateCodeBlock start");
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("Class<? super T> $L = type.getRawType()", Constant.Naming.CLAZZ_NAME);

        Set<String> javaTypeRawTypeSet = new HashSet<>();
        for (ClassPropertySolver classPropertySolver : classPropertySolverList) {
            JavaType javaType = classPropertySolver.getJavaType();
            if (!javaTypeRawTypeSet.contains(javaType.getRawType())) {
                genTypeAdapterCodeBlock(codeBlockBuilder, javaType.getTypeMirror());

                javaTypeRawTypeSet.add(javaType.getRawType());
            }
        }

        codeBlockBuilder.addStatement("return null");

        return codeBlockBuilder.build();
    }

    private void genTypeAdapterCodeBlock(@Nonnull CodeBlock.Builder codeBlockBuilder, @Nonnull TypeMirror typeMirror) {
        ClassName className = ClassName.bestGuess(typeMirror.toString());

        codeBlockBuilder.beginControlFlow("if ($L == $T.class)", Constant.Naming.CLAZZ_NAME, ClassName.bestGuess(typeMirror.toString()));
        codeBlockBuilder.addStatement("return (TypeAdapter<T>) new $T($L)", ClassName.bestGuess(ElementUtils.getPackageName(typeMirror, processingEnv) + "." + ResolverUtils.simpleName(className, logger) + Constant.Naming.TYPE_ADAPTER), ParameterName.METHOD_CREATE_GSON);
        codeBlockBuilder.endControlFlow();
    }
}
