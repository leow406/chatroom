package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.messageText.setText(message.getContent());
        holder.usernameText.setText(message.getUsername());
        holder.likeCount.setText(String.valueOf(message.getLikes()));

        holder.likeButton.setOnClickListener(v -> {
            int newLikeCount = message.getLikes() + 1;
            DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages").child(message.getKey());
            messageRef.child("likes").setValue(newLikeCount);
            message.setLikes(newLikeCount);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, usernameText, likeCount;
        ImageButton likeButton;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            usernameText = itemView.findViewById(R.id.usernameText);
            likeCount = itemView.findViewById(R.id.likeCount);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}