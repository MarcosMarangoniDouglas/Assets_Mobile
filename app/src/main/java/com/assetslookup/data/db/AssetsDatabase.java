package com.assetslookup.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.assetslookup.data.db.dao.UserDao;
import com.assetslookup.data.db.entities.User;


@Database(entities = {
  User.class,
}, version = 10, exportSchema = false)
public abstract class AssetsDatabase extends RoomDatabase {

  private static AssetsDatabase INSTANCE;

  public abstract UserDao userDao();

  public static AssetsDatabase getAppDatabase(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
              Room.databaseBuilder(context.getApplicationContext(),
                      AssetsDatabase.class,
                      "assets-database")
                      // allow queries on the main thread.
                      // Don't do this on a real app! See PersistenceBasicSample for an example.
                      .allowMainThreadQueries()
                      .fallbackToDestructiveMigration()
                      .build();
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }
}
