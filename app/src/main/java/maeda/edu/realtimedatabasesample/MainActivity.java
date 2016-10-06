package maeda.edu.realtimedatabasesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.editText)).getText().toString();
                String pass = ((EditText) findViewById(R.id.editText2)).getText().toString();

                submit(email, pass);
            }
        });


        final Query query = mDatabase.child("account").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = "";
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String key = child.getKey();
                    String account = child.getValue().toString();
                    Log.d("data", key +  account);
                    s += account + "\n";
                }
                ((TextView)findViewById(R.id.textView3)).setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void submit(String email, String pass) {
        HashMap<String, String> account = new HashMap<>();
        account.put("email", email);
        account.put("pass", pass);
        String key = mDatabase.child("account").push().getKey();
        mDatabase.child("account").child(key).setValue(account);
    }
}
