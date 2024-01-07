package zhaoxizhang.github.io.gsonenhance.model;

import zhaoxizhang.github.io.gson_enhance_annotation.GsonNewInstance;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapter;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapterTest;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/25
 */
@GsonTypeAdapter
@GsonNewInstance
public class GsonEnhanceSuperModel {
    private int superIntValue;
    private long superLongValue;
    private float superFloatValue;
    private double superDoubleValue;
    private boolean superIsBoolean;

    public int getSuperIntValue() {
        return superIntValue;
    }

    public void setSuperIntValue(int superIntValue) {
        this.superIntValue = superIntValue;
    }

    public long getSuperLongValue() {
        return superLongValue;
    }

    public void setSuperLongValue(long superLongValue) {
        this.superLongValue = superLongValue;
    }

    public float getSuperFloatValue() {
        return superFloatValue;
    }

    public void setSuperFloatValue(float superFloatValue) {
        this.superFloatValue = superFloatValue;
    }

    public double getSuperDoubleValue() {
        return superDoubleValue;
    }

    public void setSuperDoubleValue(double superDoubleValue) {
        this.superDoubleValue = superDoubleValue;
    }

    public boolean isSuperIsBoolean() {
        return superIsBoolean;
    }

    public void setSuperIsBoolean(boolean superIsBoolean) {
        this.superIsBoolean = superIsBoolean;
    }
}
