package zhaoxizhang.github.io.gson_enhance_processor.utils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;

import zhaoxizhang.github.io.gson_enhance_processor.constant.Constant;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/28
 */
public class ElementUtils {
    private static final String TAG = "ElementUtils";

    public static String getPackageName(TypeMirror typeMirror, ProcessingEnvironment processingEnv) {
        Element element = processingEnv.getTypeUtils().asElement(typeMirror);
        PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(element);
        String packageName = packageElement.getQualifiedName().toString();
        return StringUtils.isEmpty(packageName) ? Constant.Naming.PACKAGE_NAME : packageName;
    }

}
