// MessageAdapter.java
package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private String currentUsername;

    public MessageAdapter(List<Message> messageList, String currentUsername) {
        this.messageList = messageList;
        this.currentUsername = currentUsername;
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

        // Désactiver le bouton like si l'utilisateur a déjà liké le message
        if (message.getLikedByUsers().contains(currentUsername)) {
            holder.likeButton.setEnabled(false);
        } else {
            holder.likeButton.setEnabled(true);
        }

        holder.likeButton.setOnClickListener(v -> {
            if (!message.getLikedByUsers().contains(currentUsername)) {
                message.getLikedByUsers().add(currentUsername); // Ajout de l'utilisateur à la liste
                int newLikeCount = message.getLikes() + 1;
                message.setLikes(newLikeCount);

                // Mise à jour du compteur de likes et des utilisateurs ayant liké dans Firebase
                DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages").child(message.getKey());
                messageRef.child("likes").setValue(newLikeCount);
                messageRef.child("likedByUsers").setValue(message.getLikedByUsers());

                holder.likeCount.setText(String.valueOf(newLikeCount));
                holder.likeButton.setEnabled(false); // Désactiver le bouton après le like
            } else {
                Toast.makeText(holder.itemView.getContext(), "Vous avez déjà aimé ce message.", Toast.LENGTH_SHORT).show();
            }
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
