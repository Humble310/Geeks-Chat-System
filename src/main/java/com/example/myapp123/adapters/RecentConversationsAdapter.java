package com.example.myapp123.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp123.databinding.ItemContainerRecentConversationBinding;
import com.example.myapp123.listeners.ConversationListener;
import com.example.myapp123.models.ChatMessage;
import com.example.myapp123.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{
    private final List<ChatMessage>chatMessages;
    private final ConversationListener conversationListener;


    public RecentConversationsAdapter(List<ChatMessage> chatMessages , ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener=conversationListener;

    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(ItemContainerRecentConversationBinding.inflate(LayoutInflater.from(
                parent.getContext()),parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
    holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {

        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{
        ItemContainerRecentConversationBinding binding;
        ConversionViewHolder(ItemContainerRecentConversationBinding itemContainerRecentConversationBinding){
        super(itemContainerRecentConversationBinding.getRoot());
        binding=itemContainerRecentConversationBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.conversionName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v ->{
                User user=new User();
                user.id=chatMessage.conversionId;
                user.name=chatMessage.conversionName;
                user.image=chatMessage.conversionImage;
                conversationListener.onConversation(user);
            });
        }
        private Bitmap getConversionImage(String encodedImage){
            byte[]bytes= Base64.decode(encodedImage,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }
    }
}
