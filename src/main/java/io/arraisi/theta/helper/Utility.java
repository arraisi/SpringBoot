package io.arraisi.theta.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class Utility {
    public static final Gson gson = new GsonBuilder() //
            // .disableHtmlEscaping() //
            .create();

    public static boolean isNotBlank(Object object) {
        return object != null && object.toString() != null && !object.toString().isEmpty();
    }

    public static final Type typeMapOfStringObject = new TypeToken<Map<String, Object>>() {
    }.getType();
}
