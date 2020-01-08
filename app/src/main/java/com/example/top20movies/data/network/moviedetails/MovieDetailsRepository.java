package com.example.top20movies.data.network.moviedetails;

import com.example.top20movies.data.model.Movie;
import com.example.top20movies.data.model.MovieDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsRepository {

    //-------------------------- API request -------------------------------------------------------

    public void getMovieDetails(int id, Callback<MovieDetails> callback){

        MovieDetailsService service = buildRetrofit().create(MovieDetailsService.class);
        Call<MovieDetails> call = service.getMovieDetails(id);
        call.enqueue(callback);

    }

    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Cache -------------------------------------------------------------

    public void storeMovieDetails(MovieDetails movieDetails, File folder){
        Gson gson = new Gson();
        String movieDetailsJson = gson.toJson(movieDetails);

        File file = new File(folder, "movieDetails"+movieDetails.getId()+".txt");

        writeData(file, movieDetailsJson);
    }

    public MovieDetails loadMovieDetails(int id, File folder){
        File file = new File(folder, "movieDetails"+id+".txt");

        String movieDetailsJson = readData(file);

        if(movieDetailsJson == null){
            return null;
        }

        Gson gson = new Gson();
        MovieDetails movieDetails = gson.fromJson(movieDetailsJson, MovieDetails.class);

        return movieDetails;
    }

    private void writeData(File file, String data){

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private String readData(File file){

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            StringBuffer buffer = new StringBuffer();
            int read = fileInputStream.read();
            while(read != -1){
                buffer.append((char) read);
                read = fileInputStream.read();
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    //----------------------------------------------------------------------------------------------

}
