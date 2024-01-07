package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import static zhaoxizhang.github.io.gson_enhance_processor.resolver.ResolverUtils.boxPrimitive;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;
import zhaoxizhang.github.io.gson_enhance_processor.reader.TypeAdapterReaderImpl;
import zhaoxizhang.github.io.gson_enhance_processor.utils.ElementUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;
import zhaoxizhang.github.io.gson_enhance_processor.writer.TypeAdapterWriterImpl;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2023/12/10
 */
public class TypeAdapterGeneratorImpl {
    private static final String TAG = "TypeAdapterAutoGeneratorImpl";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final ClassPropertySolver classPropertySolver;
    private static final String FIELD_NAME_GSON = "gson";

    public TypeAdapterGeneratorImpl(ProcessingEnvironment processingEnv, MessageLogger logger, ClassPropertySolver classPropertySolver) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.classPropertySolver = classPropertySolver;
    }

    public void gen() {
        ClassName className = ClassName.get((TypeElement) classPropertySolver.getElement());
        logger.debug(TAG, "gen start: " + classPropertySolver.getJavaType());

        logger.debug(TAG, "readMethodSpec start");

        MethodSpec constructorMethodSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Gson.class, "gson")
                .addStatement("this.$L = $L", FIELD_NAME_GSON, "gson")
                .build();

        MethodSpec readMethodSpec = MethodSpec.methodBuilder("read")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(JsonReader.class, Constant.Naming.JSON_READER)
                .addException(IOException.class)
                .returns(className)
                .addCode(getReadCodeBlock(className))
                .build();

        logger.debug(TAG, "writeMethodSpec start");
        MethodSpec writeMethodSpec = MethodSpec.methodBuilder("write")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(JsonWriter.class, Constant.Naming.JSON_WRITER)
                .addParameter(className, Constant.TypeAdapterWriterConstant.WRITER_VALUE_KEY)
                .addException(IOException.class)
                .addCode(getWriteCodeBlock(className))
                .build();

        TypeSpec.Builder classTypeSpecBuilder = TypeSpec.classBuilder(ResolverUtils.simpleName(className, logger) + Constant.Naming.TYPE_ADAPTER)
                .superclass(ParameterizedTypeName.get(ClassName.get(TypeAdapter.class), className))
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(Gson.class, FIELD_NAME_GSON, Modifier.PRIVATE).build())
                .addFields(genFields())
                .addMethod(constructorMethodSpec)
                .addMethod(readMethodSpec)
                .addMethod(writeMethodSpec)
                .addMethods(genGetTypeAdapterMethods());

        TypeSpec classTypeSpec = classTypeSpecBuilder
                .build();

        JavaFile javaFile = JavaFile.builder(ElementUtils.getPackageName(classPropertySolver.getJavaType().getTypeMirror(), processingEnv), classTypeSpec)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            logger.error(TAG, e.getMessage());
        }
    }

    @Nonnull
    private CodeBlock getReadCodeBlock(ClassName className) {
        logger.debug(TAG, "getReadCodeBlock start");
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("$T " + Constant.TypeAdapterReaderConstant.DESERIALIZED_VALUE_KEY + " = new $T()", className, className)
                .addStatement("$L.beginObject()", Constant.Naming.JSON_READER)
                .beginControlFlow("while ($L.hasNext())", Constant.Naming.JSON_READER)
                .addStatement("String jsonName = $L.nextName()", Constant.Naming.JSON_READER)
                .beginControlFlow("switch (jsonName)");

        List<JavaField> javaFields = classPropertySolver.getJavaFields();
        for (JavaField javaField : javaFields) {
            if (!javaField.isTransient() && !javaField.isFinal()) {
                logger.debug(TAG, "getReadCodeBlock: javaFiled = " + javaField);
                codeBlockBuilder.add(((new TypeAdapterReaderImpl(processingEnv, logger, javaField)).gen()));
            }
        }

        codeBlockBuilder.add("default:\n");
        codeBlockBuilder.indent();
        codeBlockBuilder.addStatement("$L.skipValue()", Constant.Naming.JSON_READER);
        codeBlockBuilder.addStatement("break");
        codeBlockBuilder.unindent();

        codeBlockBuilder.endControlFlow()
                .endControlFlow()
                .addStatement("$L.endObject()", Constant.Naming.JSON_READER)
                .addStatement("return " + Constant.TypeAdapterReaderConstant.DESERIALIZED_VALUE_KEY);
        return codeBlockBuilder.build();
    }

    @Nonnull
    private CodeBlock getWriteCodeBlock(ClassName className) {
        logger.debug(TAG, "getWriteCodeBlock start");

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

        codeBlockBuilder.beginControlFlow("if ($L == null)", Constant.TypeAdapterWriterConstant.WRITER_VALUE_KEY);
        codeBlockBuilder.addStatement("$L.nullValue()", Constant.Naming.JSON_WRITER);
        codeBlockBuilder.addStatement("return");
        codeBlockBuilder.endControlFlow();

        codeBlockBuilder.addStatement("$L.beginObject()", Constant.Naming.JSON_WRITER);

        List<JavaField> javaFields = classPropertySolver.getJavaFields();
        for (JavaField javaField : javaFields) {
            if (!javaField.isTransient() && !javaField.isFinal()) {
                logger.debug(TAG, "getWriteCodeBlock: javaField = " + javaField);
                codeBlockBuilder.add((new TypeAdapterWriterImpl(processingEnv, logger, javaField)).gen());
            }
        }

        codeBlockBuilder.addStatement("$L.endObject()", Constant.Naming.JSON_WRITER);

        logger.debug(TAG, "getWriteCodeBlock: end");

        return codeBlockBuilder.build();
    }

    @Nonnull
    private List<FieldSpec> genFields() {
        logger.debug(TAG, "genFields");
        List<JavaField> javaFields = classPropertySolver.getJavaFields();
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        Set<TypeName> typeNameSet = new HashSet<>();

        ClassName typAdapterClassName = ClassName.get(TypeAdapter.class);
        for (JavaField javaField : javaFields) {
            JavaType javaType = javaField.getJavaType();
            TypeName typeName = boxPrimitive(javaType.getTypeName());

            logger.debug(TAG, "genFields: typeName = " + typeName);

            if (!typeNameSet.contains(typeName)) {
                typeNameSet.add(typeName);
                ParameterizedTypeName parameterizedTypeAdapter = ParameterizedTypeName.get(typAdapterClassName, typeName);
                String filedName = ResolverUtils.genTypeAdapterFieldNameWithSuffixInCamel(typeName, logger);
                logger.debug(TAG, "genFields: filedName = " + filedName);
                fieldSpecs.add(FieldSpec.builder(parameterizedTypeAdapter, filedName, Modifier.PRIVATE).build());
            }
        }
        return fieldSpecs;
    }

    @Nonnull
    private List<MethodSpec> genGetTypeAdapterMethods() {
        List<JavaField> javaFields = classPropertySolver.getJavaFields();
        List<MethodSpec> methodSpecs = new ArrayList<>();
        Set<TypeName> typeNameSet = new HashSet<>();
        for (JavaField javaField : javaFields) {
            TypeName typeName = boxPrimitive(javaField.getJavaType().getTypeName());
            if (!typeNameSet.contains(typeName)) {
                typeNameSet.add(typeName);
                methodSpecs.add(genGetTypeAdapterMethodSpec(javaField));
            }
        }
        return methodSpecs;
    }

    @Nonnull
    private MethodSpec genGetTypeAdapterMethodSpec(@Nonnull JavaField javaField) {
        JavaType javaType = javaField.getJavaType();
        TypeName typeName = boxPrimitive(javaType.getTypeName());
        ClassName typAdapterClassName = ClassName.get(TypeAdapter.class);
        ParameterizedTypeName parameterizedTypeAdapter = ParameterizedTypeName.get(typAdapterClassName, typeName);

        String typeAdapterFieldName = ResolverUtils.genTypeAdapterFieldNameWithSuffixInCamel(typeName, logger);
        return MethodSpec.methodBuilder(ResolverUtils.genTypeAdapterMethodName(typeName, logger))
                .addModifiers(Modifier.PUBLIC)
                .returns(parameterizedTypeAdapter)
                .addCode(CodeBlock.builder()
                        .beginControlFlow("if($L == null)", typeAdapterFieldName)
                        .addStatement("$L = ($T)gson.getAdapter($L)", typeAdapterFieldName, parameterizedTypeAdapter, genParameterizedGetAdapterStatement(javaField))
                        .endControlFlow()
                        .addStatement("return $L", typeAdapterFieldName)
                        .build())
                .build();
    }

    @Nonnull
    private CodeBlock genParameterizedGetAdapterStatement(@Nonnull JavaField javaField) {
        JavaType javaType = javaField.getJavaType();
        TypeName typeName = boxPrimitive(javaType.getTypeName());
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            codeBlockBuilder.add("$T.getParameterized($T.class", TypeToken.class, parameterizedTypeName.rawType);
            for (TypeName typeNameArgument : parameterizedTypeName.typeArguments) {
                buildParameterizedArguments(typeNameArgument, codeBlockBuilder);
            }
            codeBlockBuilder.add(")");
        } else if (typeName instanceof ArrayTypeName) {
            ArrayTypeName arrayTypeName = (ArrayTypeName) typeName;
            TypeName componentType = arrayTypeName.componentType;
            codeBlockBuilder.add("$T.getArray(", TypeToken.class);
            if (componentType instanceof ParameterizedTypeName) {
                buildComponentArguments(componentType, codeBlockBuilder);
                codeBlockBuilder.add(")");
            } else {
                codeBlockBuilder.add("$T.class)", componentType);
            }
        } else {
            codeBlockBuilder.add("$T.get($T.class)", TypeToken.class, typeName);
        }

        return codeBlockBuilder.build();
    }

    private void buildComponentArguments(@Nonnull TypeName typeName, @Nonnull CodeBlock.Builder codeBlockBuilder) {
        if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            codeBlockBuilder.add("$T.getParameterized($T.class", TypeToken.class, parameterizedTypeName.rawType);
            for (TypeName typeNameVariable : parameterizedTypeName.typeArguments) {
                buildParameterizedArguments(typeNameVariable, codeBlockBuilder);
            }
            codeBlockBuilder.add(").getType()");
        } else {
            codeBlockBuilder.add("$T.class", typeName);
        }
    }

    private void buildParameterizedArguments(@Nonnull TypeName typeName, @Nonnull CodeBlock.Builder codeBlockBuilder) {
        codeBlockBuilder.add(", ");
        if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            codeBlockBuilder.add("$T.getParameterized($T.class", TypeToken.class, parameterizedTypeName.rawType);
            for (TypeName typeNameVariable : parameterizedTypeName.typeArguments) {
                buildParameterizedArguments(typeNameVariable, codeBlockBuilder);
                codeBlockBuilder.add(").getType()");
            }
        } else {
            codeBlockBuilder.add("$T.class", typeName);
        }
    }

}
