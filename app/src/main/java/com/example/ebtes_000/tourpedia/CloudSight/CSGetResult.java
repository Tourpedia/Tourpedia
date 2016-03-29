package com.example.ebtes_000.tourpedia.CloudSight;

/**
 * Created by bashayer on 16/02/2016.
 */
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class CSGetResult extends GenericJson {
    @Key
    private String status;

    @Key
    private String name;

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
