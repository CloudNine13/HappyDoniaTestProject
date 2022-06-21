package com.example.happydoniatestproject.interfaces;

import androidx.recyclerview.widget.ConcatAdapter;


public interface MainPresenter {
    void askPermissions();
    void getLocation();
    void setRecyclerView(ConcatAdapter adapter);
    void setErrorMessage(String message, String title);
}
