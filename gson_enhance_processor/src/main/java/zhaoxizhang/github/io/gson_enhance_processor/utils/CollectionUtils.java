package zhaoxizhang.github.io.gson_enhance_processor.utils;

import java.util.Collection;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/28
 */
public class CollectionUtils {

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

}
