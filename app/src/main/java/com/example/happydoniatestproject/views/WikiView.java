package com.example.happydoniatestproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.example.happydoniatestproject.R;
import com.example.happydoniatestproject.interfaces.MainPresenter;
import com.example.happydoniatestproject.interfaces.MainView;
import com.example.happydoniatestproject.presenters.WikiPresenter;


public class WikiView extends AppCompatActivity implements MainView
{

    private RecyclerView recyclerView;
    private MainPresenter wikiPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        wikiPresenter = new WikiPresenter(this);

        askPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("Permission result", "Permission: " + permissions[0] + " was " + grantResults[0]);
        if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
            setErrorMessage(getResources().getString(R.string.permission_denied),
                    getResources().getString(R.string.permission_title));
        } else if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.wikiPresenter = null;
    }

    public void setRecyclerView(ConcatAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void setErrorMessage(String message, String title) {
        AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        builder.show();
    }

    private void askPermission() {
        wikiPresenter.askPermissions();
    }

    private void getLocation(){
        wikiPresenter.getLocation();
    }
}