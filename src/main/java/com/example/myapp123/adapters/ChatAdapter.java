package com.example.myapp123.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp123.databinding.ItemContainerReceivedMessageBinding;
import com.example.myapp123.databinding.ItemContainerSentMesssageBinding;
import com.example.myapp123.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ChatMessage>chatMessages;
    private  Bitmap receiverProfileImage;
    private final String senderId;
    private static final int VIEW_TYPE_SENT=1;
    private static final int VIEW_TYPE_RECEIVED=2;

    public void setReceiverProfileImage(Bitmap bitmap){
        receiverProfileImage=bitmap;
    }

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT){
                return new sentMessageViewHolder(ItemContainerSentMesssageBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                ));
        }else {
            return new ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
        ));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == VIEW_TYPE_SENT){
        ((sentMessageViewHolder) holder).setData(chatMessages.get(position));
    }else {
        ((ReceivedMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
    }
    }

    @Override
    public int getItemCount() {

        return chatMessages.size();
    }
    @Override
    public int getItemViewType(int position){
        if (chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }

      static class  sentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMesssageBinding binding;
        sentMessageViewHolder(ItemContainerSentMesssageBinding itemContainerSentMesssageBinding){
            super(itemContainerSentMesssageBinding.getRoot());
            binding=itemContainerSentMesssageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }

    }
     static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerReceivedMessageBinding binding;
        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
            super(itemContainerReceivedMessageBinding.getRoot());
            binding=itemContainerReceivedMessageBinding;
        }
        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverProfileImage!=null){
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }

        }
    }

}
