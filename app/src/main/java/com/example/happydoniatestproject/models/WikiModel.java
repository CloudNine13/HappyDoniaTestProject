package com.example.happydoniatestproject.models;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.happydoniatestproject.adapters.HeaderAdapter;
import com.example.happydoniatestproject.adapters.RecyclerViewAdapter;
import com.example.happydoniatestproject.helpers.CustomJSONParser;
import com.example.happydoniatestproject.interfaces.MainModel;
import com.example.happydoniatestproject.interfaces.MainPresenter;
import com.example.happydoniatestproject.io.WikiAPI;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WikiModel implements MainModel {

    private final MainPresenter presenter;
    private String address;
    private static final String TAG = "WikiModel.TAG";
    private static final String messageDownload = "An error happened while downloading the data";
    private static final String messageLocation = "An error happened while configuring location";
    private static final String titleDownload = "Download Error";
    private static final String titleLocation = "Location Error";

    public WikiModel(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void getLocPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 10);
    }

    public void getLocation(Activity activity) {
        boolean granted = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (granted) {
            LocationServices.getFusedLocationProviderClient(activity).getLastLocation()
                    .addOnSuccessListener(
                    activity,
                    location -> {
                        if (location != null) {
                            //0 - lat, 1 - long
                            List<Double> coordinates = new ArrayList<>();
                            coordinates.add(location.getLatitude());
                            coordinates.add(location.getLongitude());
                            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                            try {
                                List<Address> adr = geocoder.getFromLocation(coordinates.get(0),
                                        coordinates.get(1), 1);
                                String address = adr.get(0).getLocality();
                                if (address == null){
                                    coordinates.set(0, 39.466667);
                                    coordinates.set(1, -0.375000);
                                    //lat 39.466667 long -0.375000
                                    address = "Valencia";
                                }
                                this.address = address;
                                getWikiData(coordinates);
                            } catch (IOException e) {
                                Log.e(TAG, "Error occurred while configuring location: "
                                        + e.getMessage());
                                presenter.setErrorMessage(messageLocation, titleLocation);
                            }
                        }
                    }
            );
        }
    }

    private void getWikiData(List<Double> coordinates) {
        Call<ResponseBody> call = buildCall(coordinates);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                try {
                    configureAdapters(address, CustomJSONParser.ParseModel(response));
                } catch (JSONException | IOException e) {
                    Log.e(TAG, "Error occurred while configuring the adapters: "
                            + e.getMessage());
                    presenter.setErrorMessage(messageDownload, titleDownload);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "Failure occurred while downloading data: " + t.getMessage());
                presenter.setErrorMessage(messageDownload, titleDownload);
            }
        });
    }

    private Call<ResponseBody> buildCall(List<Double> coordinates) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        String url = "https://en.wikipedia.org/w/api.php?action=query&generator=geosearch" +
                "&prop=coordinates|pageimages&ggscoord=" + coordinates.get(0)
                + "|" + coordinates.get(1) + "&ggsradius=10000&format=json&codistancefrompoint="
                + coordinates.get(0) + "|" + coordinates.get(1);
        WikiAPI api = retrofit.create(WikiAPI.class);
        return api.getNearbyArticles(url);
    }

    private void configureAdapters(String address, List<Map<String, String>> data) {
        RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>
                headerAdapter = new HeaderAdapter(address);
        RecyclerView.Adapter<RecyclerViewAdapter.RVViewHolder>
                rvAdapter = new RecyclerViewAdapter(data);
        ConcatAdapter adapter = new ConcatAdapter(headerAdapter, rvAdapter);
        presenter.setRecyclerView(adapter);
    }
}
