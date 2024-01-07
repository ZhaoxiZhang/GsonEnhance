package zhaoxizhang.github.io.gsonenhance.model;

import java.util.List;

import zhaoxizhang.github.io.gson_enhance_annotation.GsonNewInstance;
import zhaoxizhang.github.io.gson_enhance_annotation.GsonTypeAdapter;

/**
 * @author https://github.com/ZhaoxiZhang
 * @date 2022/9/25
 */
@GsonTypeAdapter
@GsonNewInstance
public class ObjectModel {
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private boolean isBoolean;
    private boolean canBoolean;
    private List<String> stringList;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        isBoolean = aBoolean;
    }

    public boolean isCanBoolean() {
        return canBoolean;
    }

    public void setCanBoolean(boolean canBoolean) {
        this.canBoolean = canBoolean;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public String toString() {
        return "ObjectModel{" +
                "intValue=" + intValue +
                ", longValue=" + longValue +
                ", floatValue=" + floatValue +
                ", doubleValue=" + doubleValue +
                ", isBoolean=" + isBoolean +
                ", canBoolean=" + canBoolean +
                ", stringList=" + stringList +
                '}';
    }
}
