package com.gamechangesolutions.assignment.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamechangesolutions.assignment.R;
import com.gamechangesolutions.assignment.adapter.IssueDataAdapter;
import com.gamechangesolutions.assignment.databinding.ActivityIssueListBinding;
import com.gamechangesolutions.assignment.viewmodel.IssueListViewModel;

public class IssueListActivity extends AppCompatActivity {

    private IssueDataAdapter issueDataAdapter;
    private ActivityIssueListBinding binding;
    private IssueListViewModel issueListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue_list);
        issueListViewModel = ViewModelProviders.of(this).get(IssueListViewModel.class);
        init();
    }

    private void init() {
        RecyclerView recyclerView = binding.rvIssueList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        issueDataAdapter = new IssueDataAdapter();
        recyclerView.setAdapter(issueDataAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        issueListViewModel.getIssueList().observe(this, issues -> {
            // set the adapter with latest result
            issueDataAdapter.setIssueList(issues);
            binding.progressBar.setVisibility(View.GONE);
            binding.tvNoData.setVisibility(issues.isEmpty() ? View.VISIBLE : View.GONE);
            binding.rvIssueList.setVisibility(issues.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }
}
