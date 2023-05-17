package sv.com.guindapp.util;

import android.content.Context;

import io.objectbox.BoxStore;
import sv.com.guindapp.model.entity.MyObjectBox;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() { return boxStore; }
}
