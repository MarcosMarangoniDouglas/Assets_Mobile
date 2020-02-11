package com.assetslookup.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.assetslookup.data.db.entities.User;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insertUser(User user);

  @Query("SELECT * FROM user_table LIMIT 1")
  User getUser();

  @Query("DELETE FROM user_table")
  void deleteAllUsers();


}
