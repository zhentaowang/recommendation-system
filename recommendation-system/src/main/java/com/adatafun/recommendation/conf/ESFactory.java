package com.adatafun.recommendation.conf;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

/**
 * Created by wzt on 2017/9/3.
 */
public class ESFactory {
    private static JestHttpClient client;

    private ESFactory() {

    }

    public synchronized static JestHttpClient getClient() {
        if (client == null) {
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig.Builder(
                    "http://localhost:9200").multiThreaded(true).build());
            client = (JestHttpClient) factory.getObject();
        }
        return client;
    }
}
