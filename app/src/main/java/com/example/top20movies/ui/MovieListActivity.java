package com.example.top20movies.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.top20movies.R;
import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.network.JsonApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

        getMovies();
    }

    private void getMovies(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi api = retrofit.create(JsonApi.class);

        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if(!response.isSuccessful()){
                    tv1.setText("Erro: "+response.code());
                    return;
                }
                List<Movie> movies = response.body();
                for(Movie movie : movies){
                    tv1.append(movie.getTitle()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                tv1.setText(t.getMessage());
            }
        });





    }

}
