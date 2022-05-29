package com.example.mtchat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.mtchat.ItemClickListner;
import com.example.mtchat.Model.Message;
import com.example.mtchat.R;
import com.github.library.bubbleview.BubbleImageView;
import com.github.library.bubbleview.BubbleTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public BubbleTextView messageText;
    public BubbleImageView messageImage;
    public TextView messageUser, messageTime;
    private ItemClickListner itemClickListner;

    public MessageViewHolder(@NonNull View itemView)
    {
        super(itemView);
        messageText = itemView.findViewById(R.id.message_text);
        messageUser = itemView.findViewById(R.id.message_user);
        messageTime = itemView.findViewById(R.id.message_time);
        messageImage = itemView.findViewById(R.id.message_image);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onCLick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
