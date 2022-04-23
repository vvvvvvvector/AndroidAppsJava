package com.example.androidjetpack;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MainDao {
    // create row
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    // delete row
    @Delete
    void delete(MainData mainData);

    // delete all rows
    @Delete
    void reset(List<MainData> mainDataList);

    @Query("UPDATE my_table SET text = :sText WHERE id = :sID")
    void update(int sID, String sText);

    @Query("SELECT * FROM my_table")
    List<MainData> getAll();
}
