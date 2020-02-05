package com.gamechangesolutions.assignment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gamechangesolutions.assignment.R;
import com.gamechangesolutions.assignment.databinding.CommentItemBinding;
import com.gamechangesolutions.assignment.model.Comment;

import java.util.ArrayList;

public class CommentDataAdapter extends RecyclerView.Adapter<CommentDataAdapter.CommentViewHolder> {
    private ArrayList<Comment> commentList;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.comment_item, parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.binding.setComment(comment);
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (commentList != null && commentList.size() != 0)
            return commentList.size();
        return 0;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        final CommentItemBinding binding;

        CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
