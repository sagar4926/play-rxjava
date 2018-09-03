package com.soum4j.learn.networks;

import com.soum4j.learn.models.Todo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface Apis {
    @GET("todos")
    Observable<List<Todo>> getTodos();

    @GET("users/{id}")
    Observable<User> getUser(@Path("id") Integer id);
}
