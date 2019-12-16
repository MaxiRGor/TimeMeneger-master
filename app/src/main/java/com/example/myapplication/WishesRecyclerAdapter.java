package com.example.myapplication;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WishesRecyclerAdapter extends RecyclerView.Adapter<WishesRecyclerAdapter.WishViewHolder> {

    private Context context;
    private ArrayList<Wish> wishes;
    private DeleteItemFromDatabase deleteItemFromDatabase;

    WishesRecyclerAdapter(Context context, ArrayList<Wish> wishes) {
        this.context = context;
        this.wishes = wishes;
        this.deleteItemFromDatabase = (Wishes) context;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_item, parent, false);
        return new WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        holder.bind(wishes.get(position));
    }

    public void addWish(Wish wish){
        this.wishes.add(wish);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wishes.size();
    }


    class WishViewHolder extends RecyclerView.ViewHolder {
        WishViewHolder(@NonNull View itemView) {
            super(itemView);
            textTextView = itemView.findViewById(R.id.wish_text_text_view);
            deleteBtn = itemView.findViewById(R.id.wish_del_btn);
        }

        TextView textTextView;
        Button deleteBtn;

        void bind(final Wish wish) {
            textTextView.setText(String.valueOf(wish.getText()));
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItemFromDatabase.delete(wish.getId());
                    wishes.remove(wish);
                    notifyDataSetChanged();
                }
            });
        }
    }

}