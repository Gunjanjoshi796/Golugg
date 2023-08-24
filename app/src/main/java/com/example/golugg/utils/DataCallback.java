package com.example.golugg.utils;

public interface DataCallback<T> {
    void onSuccess(T t);
    void onError(String serverError);
}
