package edu.uw.listdatademo;

import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputControlActivity extends AppCompatActivity {

    private static final String TAG = "InputControl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_control_layout);

        //grab the search button based off its ID
        Button button = (Button) findViewById(R.id.btnSearch);

        EditText text = (EditText) findViewById(R.id.txtSearch);



        //create a listener for the button
        //button interaction listener
        button.setOnClickListener(new View.OnClickListener() {


            private int count = 1;

            @Override
            public void onClick(View v) {


                Log.v(TAG, "Button Was Pressed " + count + " Times!");
                count++;
                EditText text = (EditText) findViewById(R.id.txtSearch);
                String typed = text.getText().toString();
                Log.v(TAG, "You typed: " + typed);

            }
        });
    }
}
