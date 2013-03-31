package edu.umn.kill9.contactviewer.web;

import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.google.gson.Gson;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class JsonObject {

    public static Object getJsonObject(String url) {
        AndroidHttpClient client = null;
        try {
            client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            Gson gson = new Gson();
            return gson.fromJson(new InputStreamReader(response.getEntity().getContent()), ContactListJsonResponse.class);
        }
        catch (IOException ex) {
            Log.w("getJsonObject", "Error getting web object", ex);
        }
        finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

}
