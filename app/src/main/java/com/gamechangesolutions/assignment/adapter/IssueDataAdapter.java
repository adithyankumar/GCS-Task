package com.gamechangesolutions.assignment.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gamechangesolutions.assignment.R;
import com.gamechangesolutions.assignment.databinding.IssueItemBinding;
import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.ui.IssueDetailActivity;
import com.gamechangesolutions.assignment.utils.AlertBox;
import com.gamechangesolutions.assignment.utils.Constants;

import java.util.List;

public class IssueDataAdapter extends RecyclerView.Adapter<IssueDataAdapter.IssueViewHolder> {

    private static final String TAG = IssueDataAdapter.class.getSimpleName();
    private List<Issue> issueList;

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IssueItemBinding issueItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                        , R.layout.issue_item, parent, false);
        return new IssueViewHolder(issueItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        final Issue issue = issueList.get(position);
        holder.issueItemBinding.setIssue(issue);
    }

    @Override
    public int getItemCount() {
        if (issueList != null)
            return issueList.size();
        else
            return 0;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
        notifyDataSetChanged();
    }


    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final IssueItemBinding issueItemBinding;

        IssueViewHolder(IssueItemBinding issueItemBinding) {
            super(issueItemBinding.getRoot());
            this.issueItemBinding = issueItemBinding;
            issueItemBinding.ll.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Issue issue = issueList.get(getAdapterPosition());
            Log.d(TAG, "onIssueClick: " + issue.getNumber());

            //if no comments are available then show error popup
            if (issue.getComments() <= 0) {
                AlertBox.showErrorAlert(view.getContext(), R.string.error_no_comments);

            }
            // if comments available then move to issue detail page
            else {
                Intent intent = new Intent(view.getContext(), IssueDetailActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_ISSUE, issue);
                view.getContext().startActivity(intent);
            }


        }
    }
}
