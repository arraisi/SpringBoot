package io.arraisi.theta.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
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

    public static String querySorting(List<String> columnsName, List<Boolean> sortDesc) {
        if (columnsName.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder(" order by");
        for (int i = 0; i < columnsName.size(); i++) {
            query.append(" ");
            query.append(columnsName.get(i));
            query.append(" ");
            query.append(sortDesc.isEmpty() ? "asc" : sortDesc.get(i) ? "desc" : "asc");
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }
}
