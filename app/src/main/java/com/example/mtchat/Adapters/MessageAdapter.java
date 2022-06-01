package com.example.mtchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtchat.ItemClickListner;
import com.example.mtchat.Model.Message;
import com.example.mtchat.R;
import com.github.library.bubbleview.BubbleImageView;
import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder>
{
    private List<Message> messageList;
    private Context context;


    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        BubbleTextView messageText;
        BubbleImageView messageImage;
        TextView messageUser, messageTime;

        CustomViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            messageImage = itemView.findViewById(R.id.message_image);
            messageUser = itemView.findViewById(R.id.message_user);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.messageTime.setText(messageList.get(position).getCreated_at());
        holder.messageUser.setText(messageList.get(position).getSender());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    //    public MessageViewHolder(@NonNull View itemView)
//    {
//        super(itemView);
//        messageText = itemView.findViewById(R.id.message_text);
//        messageUser = itemView.findViewById(R.id.message_user);
//        messageTime = itemView.findViewById(R.id.message_time);
//        messageImage = itemView.findViewById(R.id.message_image);
//    }
//
//    @Override
//    public void onClick(View view, int position)
//    {
//        itemClickListner.onCLick(view, position, false);
//    }
//
//    public void setItemClickListner(ItemClickListner itemClickListner)
//    {
//        this.itemClickListner = itemClickListner;
//    }
}
