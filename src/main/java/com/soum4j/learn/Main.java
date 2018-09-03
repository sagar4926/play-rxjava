package com.soum4j.learn;

import com.soum4j.learn.models.Todo;
import com.soum4j.learn.networks.Apis;
import com.soum4j.learn.networks.User;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String... args) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();

        Apis apis = retrofit.create(Apis.class);

        System.out.println("Started");

        fetchTodosWithUsersBruteForce(apis);
        optimzedFetchTodosWithUsers(apis);

        System.out.println("Subscribed");
    }

    private static void fetchTodosWithUsersBruteForce(Apis apis) {
        Date d1 = new Date();
        Disposable d = apis.getTodos()
            .flatMap(Observable::fromIterable)
            .flatMap(todo -> apis.getUser(todo.getUserId()).flatMap(user -> {
                todo.setUser(user);
                return Observable.just(todo);
            }))
            .subscribe(System.out::println, Throwable::printStackTrace, () -> {
                Date d2 = new Date();
                System.out.println("Time taken " + (d2.getTime() - d1.getTime()));
                System.out.println("Done");
            });
    }

    private static void optimzedFetchTodosWithUsers(Apis apis) {
        Date d1 = new Date();
        Disposable d = apis.getTodos().flatMap(todos -> {
            List<Observable<User>> apiCalls = todos.stream()
                    .map(Todo::getUserId)
                    .distinct()
                    .map(apis::getUser)
                    .collect(Collectors.toList());
            return Observable.zipIterable(apiCalls, objects -> {
                Map<Integer, User> map = new HashMap<>();
                for (Object object : objects) {
                    User u = ((User) object);
                    map.put(u.getId(), u);
                }
                return map;
            }, false, 10).flatMap(integerUserMap -> {
                List<Todo> list = todos.stream().peek(todo -> todo.setUser(integerUserMap.get(todo.getUserId()))).collect(Collectors.toList());
                return Observable.fromIterable(list);
            });
        }).subscribe(System.out::println, Throwable::printStackTrace, () -> {
            Date d2 = new Date();
            System.out.println("Time taken " + (d2.getTime() - d1.getTime()));
        });
    }
}
