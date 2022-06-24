package ch.hftm.schulaufgaben.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ch.hftm.schulaufgaben.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
