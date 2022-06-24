package ch.hftm.schulaufgaben.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ch.hftm.schulaufgaben.model.User;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    User getFirstUser();

    @Query("SELECT * FROM user ")
    List<User> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAllUserObservable();
}
