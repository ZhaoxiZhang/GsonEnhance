package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.utils.StringUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaType;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/26
 */
public class ClassPropertySolver {
    private static final String TAG = "ClassPropertySolver";
    private final ProcessingEnvironment processingEnv;
    private final MessageLogger logger;
    private final Element element;
    private List<JavaField> javaFields;
    private JavaType javaType;

    public ClassPropertySolver(ProcessingEnvironment processingEnv, MessageLogger logger, Element element) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public List<JavaField> getJavaFields() {
        if (javaFields == null) {
            javaFields = new ArrayList<>();
            TypeElement solvedElement = (TypeElement) element;
            while (solvedElement.getKind().isClass() && !StringUtils.equals(solvedElement.getQualifiedName().toString(), Object.class.getCanonicalName())) {
                List<? extends Element> elements = solvedElement.getEnclosedElements();
                for (Element element : elements) {
                    if (element.getKind().isField()) {
                        JavaPropertyResolver javaPropertyResolver = new JavaPropertyResolver(processingEnv, logger, element);
                        JavaField javaField = javaPropertyResolver.resolveJavaField();
                        javaFields.add(javaField);

                        logger.debug(TAG, "getJavaFields: javaField = " + javaField.toString());
                    }
                }
                solvedElement = getParentTypeElement(solvedElement);
            }
        }
        return javaFields;
    }

    public JavaType getJavaType() {
        if (javaType == null) {
            JavaTypeResolver javaTypeResolver = new JavaTypeResolver(processingEnv, logger, element);
            javaType = javaTypeResolver.resolveJavaType();
        }
        return javaType;
    }

    private TypeElement getParentTypeElement(TypeElement typeElement) {
        TypeMirror typeMirror = typeElement.getSuperclass();
        DeclaredType declaredType = (DeclaredType) typeMirror;
        return (TypeElement) declaredType.asElement();
    }
}
