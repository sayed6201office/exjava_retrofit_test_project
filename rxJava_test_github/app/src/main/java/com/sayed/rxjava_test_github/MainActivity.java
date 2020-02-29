package com.sayed.rxjava_test_github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sayed.rxjava_test_github.adaper.GitHubRepoAdapter;
import com.sayed.rxjava_test_github.model.GitHubRepo;
import com.sayed.rxjava_test_github.model.Task;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter = new GitHubRepoAdapter();
    private Subscription subscription;
    private Subscription subscriptionExaple1;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);

        //api calling github starred repo fetch
        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });


        //simple example 1, iterating list of object

        Observable<Task> taskObservable = Observable // create a new Observable object
                .from(Task.createTasksList()) // apply 'fromIterable' operator
                .subscribeOn(Schedulers.io()) ;// designate worker thread (background)
//            .observeOn(AndroidSchedulers.mainThread()); // designate observer thread (main thread)

        subscriptionExaple1 = taskObservable.subscribe(new Observer<Task>() {

            @Override
            public void onNext(Task task) { // run on main thread
                SystemClock.sleep(1000);
                Log.d(TAG, "onNext: : " + task.getDescription());
            }

            @Override
            public void onCompleted() {

            }

            @Override
           public void onError(Throwable e) {

            }
        });
    }

    @Override protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (subscriptionExaple1 != null && !subscriptionExaple1.isUnsubscribed()) {
            subscriptionExaple1.unsubscribe();
        }
        super.onDestroy();
    }

    //fetch githjub star repo and feed to arraylist.............
    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<GitHubRepo>>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(List<GitHubRepo> gitHubRepos) {
                        Log.d(TAG, "In onNext()");
                        adapter.setGitHubRepos(gitHubRepos);
                    }
                });
    }
}