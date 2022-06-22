package ch.hftm.schulaufgaben.api;

import java.util.List;

import ch.hftm.schulaufgaben.model.EventData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ReceiveRestData {

    @GET("v1/events")
    Call<List<EventData>> getEvents();
}