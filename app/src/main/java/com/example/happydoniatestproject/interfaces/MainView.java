package com.example.happydoniatestproject.interfaces;

import androidx.recyclerview.widget.ConcatAdapter;


public interface MainView {
    void setRecyclerView(ConcatAdapter adapter);
    void setErrorMessage(String message, String title);
}
