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

    public String sendGetRequest(String targetUrl) {
        HttpGet request = new HttpGet(targetUrl);
        System.out.println(request.toString());

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity responseEntity = response.getEntity();
            Header headers = responseEntity.getContentType();
            System.out.println(headers);

            if (responseEntity != null) {
                // return it as a String
                return EntityUtils.toString(responseEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
