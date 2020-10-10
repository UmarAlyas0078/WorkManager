package com.example.workmanager.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArticalRoom {
    @PrimaryKey(autoGenerate = true)
    int articalId;
    String title;
    String auther;
    String description;
    String pubslisherAt;
    String urlToImage;

    public ArticalRoom(String title, String auther, String description, String pubslisherAt, String urlToImage) {
        this.title = title;
        this.auther = auther;
        this.description = description;
        this.pubslisherAt = pubslisherAt;
        this.urlToImage = urlToImage;
    }

    public int getArticalId() {
        return articalId;
    }

    public void setArticalId(int articalId) {
        this.articalId = articalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubslisherAt() {
        return pubslisherAt;
    }

    public void setPubslisherAt(String pubslisherAt) {
        this.pubslisherAt = pubslisherAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
