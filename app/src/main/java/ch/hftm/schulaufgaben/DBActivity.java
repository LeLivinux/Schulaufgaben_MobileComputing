package ch.hftm.schulaufgaben;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.hftm.schulaufgaben.database.AppDatabase;
import ch.hftm.schulaufgaben.model.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DBActivity extends AppCompatActivity {

    private AppDatabase db;
    private ExecutorService pool;
    private EditText firstnameEdit;
    private EditText lastnameEdit;
    private TextView userlistTextView;
    private final CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);

        this.db = createDatabase();
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        this.firstnameEdit = (EditText) findViewById(R.id.firstnameField);
        this.lastnameEdit = (EditText) findViewById(R.id.lastnameField);
        this.userlistTextView = (TextView) findViewById(R.id.userList);
    }

    public void saveView(View view) {
        Log.d("DBActivity", "saveView: ");
        User auser = new User(this.firstnameEdit.getText().toString(),this.lastnameEdit.getText().toString());
        this.pool.execute(() -> {
            this.db.userDao().insertUser(auser);
        });

            StringBuilder returnString = new StringBuilder();
            List<User> userlist = db.userDao().getAllUser();
            for (User user : userlist){
                returnString.append(user.firstname).append(" ").append(user.lastname).append("\n");
            }
            userlistTextView.setText(returnString.toString());

    }

    @Override
    protected void onStart(){
        super.onStart();
        this.disposable.add(this.db.userDao().getAllUserObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userlist -> {
                            StringBuilder returnString = new StringBuilder();
                            for (User user : userlist){
                                returnString.append(user.firstname).append(" ").append(user.lastname).append("\n");
                            }
                            userlistTextView.setText(returnString.toString());
                        },
                        userlist -> Log.e(TAG, "Unable to get value from database", userlist)));


    }

    private AppDatabase createDatabase(){
        return Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"user").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}