package zhaoxizhang.github.io.gson_enhance_processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/8/23
 */
public class MessageLogger {
    private final ProcessingEnvironment processingEnv;
    private boolean verbose = false;

    public MessageLogger(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void debug(String tag, String message) {
        if (verbose) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER,
                    tag + ": " + message);
        }
    }

    public void info(String tag, String message) {
        if (verbose) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    tag + ": " + message);
        }
    }

    public void warn(String tag, String message) {
        if (verbose) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                    tag + ": " + message);
        }
    }

    public void error(String tag, String message) {
        if (verbose) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    tag + ": " + message);
        }
    }
}
