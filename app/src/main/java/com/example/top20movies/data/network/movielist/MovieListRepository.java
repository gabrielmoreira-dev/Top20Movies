package com.example.top20movies.data.network.movielist;

import com.example.top20movies.data.model.Movie;
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

public class MovieListRepository {

    //-------------------------- API request -------------------------------------------------------

    public void getMovieList(Callback<List<Movie>> callback) {

        MovieListService service = buildRetrofit().create(MovieListService.class);
        Call<List<Movie>> call = service.getMovies();
        call.enqueue(callback);

    }

    private static Retrofit buildRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile.nyc3.digitaloceanspaces.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Cache -------------------------------------------------------------

    public void storeMovieList(List<Movie> movieList, File folder){
        Gson gson = new Gson();
        String movieListJson = gson.toJson(movieList);

        File file = new File(folder, "movieList.txt");

        writeData(file, movieListJson);
    }

    public List<Movie> loadMovieList(File folder){
        File file = new File(folder, "movieList.txt");

        String movieListJson = readData(file);

        if(movieListJson == null){
            return null;
        }

        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>(){}.getType();
        List<Movie> movieList = gson.fromJson(movieListJson, movieListType);

        return movieList;
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
