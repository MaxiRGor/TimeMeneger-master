package com.example.myapplication.secret;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SecretsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SECRETS = "secrets";
    public static final String USER_UID = "userUid";
    public static final String TEXT = "text";
    public static final String ID = "id";
    private EditText secretEditText;
    private SecretsAdapter secretsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secrets);
        this.secretEditText = findViewById(R.id.secret_input_edit_text);

        ImageButton addSecretButton = findViewById(R.id.secret_add_button);
        addSecretButton.setOnClickListener(this);
        setRecyclerView();
        loadSecrets();
    }

    private void setRecyclerView() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            this.secretsAdapter = new SecretsAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RecyclerView secretsRecyclerView = findViewById(R.id.secret_recycler_view);
        secretsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        secretsRecyclerView.setAdapter(this.secretsAdapter);

        //swiping to remove
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                SecretsActivity.this.secretsAdapter.removeSecret(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(secretsRecyclerView);
    }


    @Override
    public void onClick(View view) {
        String secretText = this.secretEditText.getText().toString();
        if (!secretText.isEmpty()) {
            SecretsActivity.this.secretsAdapter.addSecret(secretText);
        }
    }

    private void loadSecrets() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseFirestore.getInstance()
                    .collection(SECRETS)
                    .whereEqualTo(USER_UID, FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots)
                        SecretsActivity.this.secretsAdapter.loadSecret(snapshot.toObject(Secret.class));
                    if(queryDocumentSnapshots.size()==0)
                        Toast.makeText(SecretsActivity.this, "Вы пока не добавили ни одной записи",
                                Toast.LENGTH_SHORT).show();
                }
            });
    }

}
