package ch.hftm.schulaufgaben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static Context ApplicationContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationContext = getApplicationContext();

    }

    public void navigateToSwitchView(View view) {
        Log.d("MainActivity", "navigateToSwitchView: ");
        Intent goToDetail = new Intent(getApplicationContext(),SwitchViewActivity.class);
        startActivity(goToDetail);
    }

    public void navigateToServerSocket(View view) {
        Log.d("MainActivity", "navigateToServerSocket: ");
        Intent goToDetail = new Intent(getApplicationContext(),ServerSocketActivity.class);
        startActivity(goToDetail);
    }

    public void navigateToApi(View view) {
        Log.d("MainActivity", "navigateToApi: ");
        Intent goToDetail = new Intent(getApplicationContext(),RetrofitActivity.class);
        startActivity(goToDetail);
    }
}