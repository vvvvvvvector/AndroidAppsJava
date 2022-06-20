package com.example.androidjetpack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// table name haha
@Entity(tableName = "my_table")

public class MainData implements Serializable {
    // id column
    @PrimaryKey(autoGenerate = true)
    private int id;

    // text column
    @ColumnInfo(name = "text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
