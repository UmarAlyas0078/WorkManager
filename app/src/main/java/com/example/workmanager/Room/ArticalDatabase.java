package com.example.workmanager.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.workmanager.Article.Article;

@Database(entities = {Article.class}, version = 2, exportSchema = false)
public abstract class ArticalDatabase extends RoomDatabase {
    public abstract ArticalDao articalDao();
}
