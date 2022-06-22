package ch.hftm.schulaufgaben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import ch.hftm.schulaufgaben.api.ReceiveRestData;
import ch.hftm.schulaufgaben.model.EventData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        TextView eventView = findViewById(R.id.event_textView);
        Call<List<EventData>> eventCall = createRetrofitClient("https://whattodo-backend.azurewebsites.net/api/").getEvents();
        eventCall.enqueue(new Callback<List<EventData>>() {
            @Override
            public void onResponse(Call<List<EventData>> call, Response<List<EventData>> response) {
                eventView.setText(response.body().get(0).title);
            }

            @Override
            public void onFailure(Call<List<EventData>> call, Throwable t) {

            }
        });
    }

    public ReceiveRestData createRetrofitClient(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReceiveRestData.class);
    }
}