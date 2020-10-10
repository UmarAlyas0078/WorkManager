package com.example.workmanager.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.workmanager.Article.Article;

import java.util.List;

@Dao
public interface ArticalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveArtical(List<Article> articalRoom);

    @Query("SELECT * FROM Article")
    LiveData<List<Article>> getArtical();

    @Query("Delete From Article")
    void deleteData();
}
