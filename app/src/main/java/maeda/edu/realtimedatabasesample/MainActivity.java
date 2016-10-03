package maeda.edu.realtimedatabasesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        final TextView emailTextView = (TextView)findViewById(R.id.textView7);
        final TextView passTextView = (TextView)findViewById(R.id.textView9);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.editText)).getText().toString();
                String pass = ((EditText)findViewById(R.id.editText2)).getText().toString();

                submit(email, pass);
            }
        });

        database.getReference("account").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> account = (HashMap<String, String>)dataSnapshot.getValue();
                emailTextView.setText(account.get("email"));
                passTextView.setText(account.get("pass"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void submit(String email, String pass){
        HashMap<String, String> account = new HashMap<>();
        account.put("email", email);
        account.put("pass", pass);

        DatabaseReference refAccount = database.getReference("account");
        refAccount.setValue(account);
    }
}
