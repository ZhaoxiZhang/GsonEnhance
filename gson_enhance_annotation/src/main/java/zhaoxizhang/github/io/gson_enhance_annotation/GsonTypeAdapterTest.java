package zhaoxizhang.github.io.gson_enhance_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface GsonTypeAdapterTest {
}
