package com.example.happydoniatestproject.presenters;

import android.app.Activity;
import androidx.recyclerview.widget.ConcatAdapter;
import com.example.happydoniatestproject.interfaces.MainModel;
import com.example.happydoniatestproject.interfaces.MainPresenter;
import com.example.happydoniatestproject.interfaces.MainView;
import com.example.happydoniatestproject.models.WikiModel;


public class WikiPresenter implements MainPresenter {

    private final MainView view;
    private final MainModel model;

    public WikiPresenter(MainView view) {
        this.view = view;
        this.model = new WikiModel(this);
    }

    public void askPermissions() {
        if (model != null) {
            model.getLocPermission((Activity) view);
        }
    }

    public void getLocation() {
        if (model != null) {
            model.getLocation((Activity) view);
        }
    }

    public void setRecyclerView(ConcatAdapter adapter) {
        view.setRecyclerView(adapter);
    }

    public void setErrorMessage(String message, String title){
        view.setErrorMessage(message, title);
    }
}
