package zhaoxizhang.github.io.gson_enhance_processor;

import com.google.auto.service.AutoService;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import zhaoxizhang.github.io.gson_enhance_annotation.GsonNewInstance;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapter;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.ClassPropertySolver;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.InstanceCreatorGeneratorImpl;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.TypeAdapterGeneratorImpl;
import zhaoxizhang.github.io.gson_enhance_processor.resolver.TypeAdapterFactoryGeneratorImpl;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/17
 */
@AutoService(Processor.class)
@SupportedOptions(value = {"verbose"})
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
public class GsonEnhanceProcessor extends AbstractProcessor {
    private static final String OPTION_VERBOSE = "verbose";
    private Messager messager;
    private MessageLogger messageLogger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        messageLogger = new MessageLogger(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean verbose = Boolean.parseBoolean(processingEnv.getOptions().get(OPTION_VERBOSE));
        messageLogger.setVerbose(verbose);

        messageLogger.debug("gsonprocessor", "process");

        Set<TypeElement> gsonNewInstanceElementSet = new LinkedHashSet<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(GsonNewInstance.class)) {
            if (element.getKind().isClass()) {
                gsonNewInstanceElementSet.add((TypeElement) element);
            }
        }

        if (!gsonNewInstanceElementSet.isEmpty()) {
            InstanceCreatorGeneratorImpl instanceCreatorGenerator = new InstanceCreatorGeneratorImpl(processingEnv, messageLogger, gsonNewInstanceElementSet);
            instanceCreatorGenerator.gen();
        }

        Set<Element> gsonTypeAdapterElementSet = new LinkedHashSet<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(GsonTypeAdapter.class)) {
            if (element.getKind().isClass()) {
                gsonTypeAdapterElementSet.add(element);
            }
        }

        if (!gsonTypeAdapterElementSet.isEmpty()) {
            generateGsonTypeAdapter(gsonTypeAdapterElementSet);
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(GsonNewInstance.class.getCanonicalName());
        annotationTypes.add(GsonTypeAdapter.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void generateGsonTypeAdapter(Set<Element> typeElementSet) {
        List<ClassPropertySolver> classPropertySolverList = new ArrayList<>(typeElementSet.size());
        for (Element typeElement : typeElementSet) {
            messageLogger.debug("generateGsonTypeAdapter", "typeElement: " + typeElement);
            ClassPropertySolver classPropertySolver = new ClassPropertySolver(processingEnv, messageLogger, typeElement);
            TypeAdapterGeneratorImpl typeAdapterGenerator = new TypeAdapterGeneratorImpl(processingEnv, messageLogger, classPropertySolver);
            typeAdapterGenerator.gen();
            classPropertySolverList.add(classPropertySolver);
        }
        if (!classPropertySolverList.isEmpty()) {
            TypeAdapterFactoryGeneratorImpl typeAdapterFactoryGenerator = new TypeAdapterFactoryGeneratorImpl(processingEnv, messageLogger, classPropertySolverList);
            typeAdapterFactoryGenerator.gen();
        }
    }
}