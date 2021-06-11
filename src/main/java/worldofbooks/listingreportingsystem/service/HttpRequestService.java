package worldofbooks.listingreportingsystem.service;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpRequestService {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public void sendGetRequest(String targetUrl) {
        HttpGet request = new HttpGet(targetUrl);

        System.out.println(request.toString());
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
