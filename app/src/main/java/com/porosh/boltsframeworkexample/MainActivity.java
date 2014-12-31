package com.porosh.boltsframeworkexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import bolts.Continuation;
import bolts.Task;





public class MainActivity extends ActionBarActivity {

    static final String TAG = "com.porosh.boltsframeworkexample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"hiiii");


        runSingleTask();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runSingleTask() {
        asyncMethod("Hello world").continueWith(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                Log.v(TAG, "Task finished : " + task.getResult());
                return null;
            }
        });
    }


    public Task<String> asyncMethod(final String param) {
        final Task<String>.TaskCompletionSource task = Task.<String> create();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, "Running task (" + param + ")");
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.setResult(param);
                //task.setError(new RuntimeException("An error message."));
            }
        }).start();

        return task.getTask();
    }



}
