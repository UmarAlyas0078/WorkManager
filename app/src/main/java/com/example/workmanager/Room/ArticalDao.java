package com.example.workmanager.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workmanager.Article.Article;

import java.util.List;

@Dao
public interface ArticalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveArtical(Article articalRoom);

    @Query("SELECT * FROM Article")
    List<Article> getArtical();
}
