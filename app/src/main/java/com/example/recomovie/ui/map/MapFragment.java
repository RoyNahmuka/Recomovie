package com.example.recomovie.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.recomovie.R;
import com.example.recomovie.model.Model;
import com.example.recomovie.model.Review;
import com.example.recomovie.model.movie.Movie;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment {

    List<Movie> movies;
    HashMap<String,GeoPoint> geoMovieMap = new HashMap<String,GeoPoint>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        LiveData <List<Review>> list = Model.instance.getAll();
        HashMap<String,Integer> MovieCountMap = new HashMap<String,Integer>();

        for (Review review: list.getValue()){
            if(!MovieCountMap.containsKey(review.getMovieName())){
                MovieCountMap.put(review.getMovieName(),1);
            }
            else {
                MovieCountMap.put(review.getMovieName(),MovieCountMap.get(review.getMovieName()) + 1);
            }
        }
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                // Add a marker in Sydney and move the camera
                Model.instance.getMoviesFirebase(movieList-> {
                    movies = movieList;

                    for (Movie movie:movies){
                        geoMovieMap.put(movie.getName(),movie.getGeoPoint());
                    }

                for (String key: MovieCountMap.keySet()){
                    int counter;
                    counter = MovieCountMap.get(key);
                    GeoPoint geo = geoMovieMap.get(key);

                    LatLng latLng = new LatLng(geo.getLatitude(), geo.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(key + ": " + "have " + counter + " reviews"));
                        // below line is use to add custom marker on our map
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 2
                        ));
                        googleMap.addMarker(markerOptions);
                    }
                });
                }
                });
            }
        });
        return view;
    }
}