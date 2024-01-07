package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Type;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/25
 */
public class InstanceCreatorGeneratorImpl {
    private static final String TAG = "InstanceCreatorGeneratorImpl";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final Set<TypeElement> typeElementSet;

    public InstanceCreatorGeneratorImpl(ProcessingEnvironment processingEnv, MessageLogger logger, Set<TypeElement> typeElementSet) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.typeElementSet = typeElementSet;
    }

    public void gen() {
        logger.debug(TAG, "gen start");

        MethodSpec methodSpec = MethodSpec.methodBuilder("registerInstanceCreator")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(GsonBuilder.class, Constant.Naming.GSON_BUILDER_NAME)
                .addCode(gsonBoostInstanceCreatorCodeBlock(typeElementSet))
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(Constant.Naming.GSON_ENHANCE_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(Constant.Naming.PACKAGE_NAME, typeSpec).build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            logger.debug(TAG, e.getMessage());
        }

        logger.debug(TAG, "gen end");
    }

    private CodeBlock gsonBoostInstanceCreatorCodeBlock(Set<TypeElement> typeElementSet) {
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (TypeElement typeElement : typeElementSet) {
            if (typeElement != null) {
                ClassName className = ClassName.bestGuess(typeElement.getQualifiedName().toString());
                codeBlockBuilder.addStatement("$L.registerTypeAdapter($T.class, $L)", Constant.Naming.GSON_BUILDER_NAME,
                        className,
                        TypeSpec.anonymousClassBuilder("")
                                .superclass(ParameterizedTypeName.get(ClassName.get(InstanceCreator.class), className))
                                .addMethod(MethodSpec.methodBuilder("createInstance")
                                        .addModifiers(Modifier.PUBLIC)
                                        .addParameter(Type.class, "type")
                                        .addAnnotation(Override.class)
                                        .addStatement("return new $T()", className)
                                        .returns(className)
                                        .build())
                                .build());
            }
        }
        return codeBlockBuilder.build();
    }
}
