package bodycap.com.etactsample.models;

import bodycap.com.etactsample.R;

/**
 * Created by Laurent on 24/05/2017.
 */

public class DataModel {

    private String mValue;
    private int mImageId;

    public static final int BATTERY = R.drawable.battery;
    public static final int TEMPERATURE = R.drawable.temperature;
    public static final int ACTIVITY = R.drawable.activity;
    public static final int AXES = R.drawable.axes;

    public DataModel(int id, String value) {
        this.mValue = value;
        this.mImageId = id;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public int getmImageId() {
        return mImageId;
    }
}
