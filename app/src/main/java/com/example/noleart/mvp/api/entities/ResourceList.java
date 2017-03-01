package com.example.noleart.mvp.api.entities;

import java.util.List;


/**
 * Created by noleart on 10/02/17.
 */

public class ResourceList {
    public int available;
    public int returned;
    public String collectionUri;
    public List<ResumeEntity> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getCollectionUri() {
        return collectionUri;
    }

    public void setCollectionUri(String collectionUri) {
        this.collectionUri = collectionUri;
    }

    public List<ResumeEntity> getItems() {
        return items;
    }

    public void setItems(List<ResumeEntity> items) {
        this.items = items;
    }
}
