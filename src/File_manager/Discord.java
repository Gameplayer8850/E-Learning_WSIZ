package File_manager;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Discord {

    HttpClient httpClient;
    HttpResponse response;
    HttpPost request;

    public void Send(String url, String data) {
        if(data.equals("")) return;
        httpClient = HttpClientBuilder.create().build();
        request = new HttpPost(url);
        request.addHeader("Content-Type", "application/json");
        String jsonMessage = "{\"content\": \"" + data + "\"}";
        try {
            StringEntity params = new StringEntity(jsonMessage, "UTF-8");
            request.setEntity(params);
            response = httpClient.execute(request);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }
}
