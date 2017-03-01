package com.example.noleart.mvp.api.database;

import com.example.noleart.mvp.api.entities.Image;
import com.example.noleart.mvp.api.entities.ResourceList;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by noleart on 15/02/17.
 */

@Table(database = MyDatabase.class)
public class Favourite extends BaseModel {

    @Column
    @PrimaryKey
    int id;

    @Column
    boolean favourite;

    @Column
    String name;

    @Column
    String description;

    @Column
    String thumbnail;

    @Column
    String detailUrl;

    @Column
    String wikiUrl;

    @Column
    String comiclinkUrl;

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public String getComiclinkUrl() {
        return comiclinkUrl;
    }

    public void setComiclinkUrl(String comiclinkUrl) {
        this.comiclinkUrl = comiclinkUrl;
    }
    /*   @Column
    ResourceList comics;

    @Column
    ResourceList events;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

   /* public ResourceList getComics() {
        return comics;
    }

    public void setComics(ResourceList comics) {
        this.comics = comics;
    }

    public ResourceList getEvents() {
        return events;
    }

    public void setEvents(ResourceList events) {
        this.events = events;
    }*/
}
