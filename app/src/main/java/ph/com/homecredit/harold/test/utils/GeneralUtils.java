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
import java.util.Random;

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

    public static int randomInt(int range){
        return randomInt(0, range);
    }

    public static int randomInt(int min, int max){
        return new Random().nextInt(max - min) + min;
    }

    public static String getRandomString(final int size) {
        String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static int GenerateId(){
        int num = GeneralUtils.randomInt(1, 2147483647);
        return num - (num * 2);
    }
}
