package com.example.ebtes_000.tourpedia.CloudSight;

/**
 * Created by bashayer on 16/02/2016.
 */
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/* this code is reused */
public class CSPostResult extends GenericJson {
    @Key
    private String token;

    @Key
    private String url;

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }
}

