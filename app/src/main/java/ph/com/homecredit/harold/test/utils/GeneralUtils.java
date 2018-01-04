package ph.com.homecredit.harold.test.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Harold Reyes on 1/4/2018.
 */

public class GeneralUtils {


    public static String getStringFromRAW(Context context, int rawId) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream is = context.getResources().openRawResource(rawId)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }

        return writer.toString();
    }

    public static JSONObject getJSONObjectFromRaw(Context context, int rawId) throws IOException, JSONException {
        return new JSONObject(getStringFromRAW(context, rawId));
    }

    public static JSONArray getJSONArrayFromRaw(Context context, int rawId) throws IOException, JSONException {
        return new JSONArray(getStringFromRAW(context, rawId));
    }


}
