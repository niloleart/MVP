package com.example.noleart.mvp.api.entities;

import java.util.List;

/**
 * Created by noleart on 3/02/17.
 */

public class Characters {

    private int id;
    private String name;
    private String description;
    private String modified;
    private Image thumbnail;
    private String resourceURI;
    private List<Url> urls;
    private ResourceList  stories;
    private ResourceList events;
    private ResourceList series;
    private ResourceList comics;
    private boolean favourite;

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public ResourceList getStories() {
        return stories;
    }

    public void setStories(ResourceList stories) {
        this.stories = stories;
    }

    public ResourceList getEvents() {
        return events;
    }

    public void setEvents(ResourceList events) {
        this.events = events;
    }

    public ResourceList getSeries() {
        return series;
    }

    public void setSeries(ResourceList series) {
        this.series = series;
    }

    public ResourceList getComics() {
        return comics;
    }

    public void setComics(ResourceList comics) {
        this.comics = comics;
    }

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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
}
