package zhaoxizhang.github.io.gson_enhance_processor.resolver;

import javax.annotation.processing.ProcessingEnvironment;

import zhaoxizhang.github.io.gson_enhance_processor.MessageLogger;
import zhaoxizhang.github.io.gson_enhance_processor.utils.StringUtils;
import zhaoxizhang.github.io.gson_enhance_processor.vo.JavaField;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/23
 */
public abstract class AbstractTypeAdapter {
    protected static final int ASCII_LOWER_A = 97;
    protected static final int ASCII_LOWER_Z = 122;
    protected static final int ASCII_UPPER_A = 65;
    protected static final int ASCII_UPPER_Z = 90;
    protected static final String IS_PREFIX = "is";
    private static final char UNDER_LINE = '_';
    protected ProcessingEnvironment processingEnv;
    protected MessageLogger logger;
    protected JavaField javaField;

    public AbstractTypeAdapter(ProcessingEnvironment processingEnv, MessageLogger logger, JavaField javaField) {
        this.processingEnv = processingEnv;
        this.logger = logger;
        this.javaField = javaField;
    }

    public abstract String getTag();

    /**
     * 下划线转驼峰
     */
    protected String toCamelCase(String name) {
        if (StringUtils.isEmpty(name)) {

            return name;
        }

        int length = name.length();
        StringBuilder sb = new StringBuilder(length);
        boolean underLineNextChar = false;

        for (int i = 0; i < length; ++i) {
            char c = name.charAt(i);
            if (c == UNDER_LINE) {
                underLineNextChar = true;
            } else if (underLineNextChar) {
                sb.append(Character.toUpperCase(c));
                underLineNextChar = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    protected String toUnderCase(String name) {
        if (name == null) {
            return null;
        }

        int len = name.length();
        StringBuilder res = new StringBuilder(len + 2);
        char pre = 0;
        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (pre != UNDER_LINE) {
                    res.append(UNDER_LINE);
                }
                res.append(Character.toLowerCase(ch));
            } else {
                res.append(ch);
            }
            pre = ch;
        }
        return res.toString();
    }
}
