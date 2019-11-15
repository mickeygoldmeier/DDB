package com.example.ddb.Data;

public interface NotifyDataChange<T> {
    void OnDataChanged(T obj);

    void onFailure(Exception exception);
}
