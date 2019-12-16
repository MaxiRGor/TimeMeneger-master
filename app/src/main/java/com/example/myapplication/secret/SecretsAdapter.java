package com.example.myapplication.secret;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecretsAdapter extends RecyclerView.Adapter<SecretsAdapter.SecretsViewHolder> {

    private ArrayList<Secret> secrets;
    private String userUid;

    SecretsAdapter(String userUid) {
        this.secrets = new ArrayList<>();
        this.userUid = userUid;
    }

    void loadSecret(Secret secret) {
        secrets.add(secret);
        notifyItemInserted(secrets.size());
    }

    void addSecret(String secretText) {
        Map<String, Object> data = new HashMap<>();
        data.put(SecretsActivity.TEXT, secretText);
        data.put(SecretsActivity.USER_UID, userUid);

        //create document to fill it's id field
        DocumentReference reference = FirebaseFirestore
                .getInstance().collection(SecretsActivity.SECRETS)
                .document();
        data.put(SecretsActivity.ID, reference.getId());
        reference.set(data);
        //document now is on firestore

        loadSecret(new Secret(reference.getId(), secretText, userUid));
    }

    void removeSecret(int adapterPosition) {
        FirebaseFirestore.getInstance()
                .collection(SecretsActivity.SECRETS)
                .document(secrets.get(adapterPosition).getId())
                .delete();
        secrets.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    @NonNull
    @Override
    public SecretsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.secret_item, parent, false);
        return new SecretsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecretsViewHolder holder, int position) {
        holder.bind(secrets.get(position));
    }

    @Override
    public int getItemCount() {
        return secrets.size();
    }


    class SecretsViewHolder extends RecyclerView.ViewHolder {
        private TextView secretItemTextView;

        SecretsViewHolder(View itemView) {
            super(itemView);
            secretItemTextView = itemView.findViewById(R.id.secret_item_text_view);
        }

        void bind(final Secret secret) {
            secretItemTextView.setText(secret.getText());
        }
    }
}
