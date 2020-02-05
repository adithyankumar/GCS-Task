package com.gamechangesolutions.assignment.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamechangesolutions.assignment.R;
import com.gamechangesolutions.assignment.adapter.CommentDataAdapter;
import com.gamechangesolutions.assignment.databinding.ActivityDetailBinding;
import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.utils.Constants;
import com.gamechangesolutions.assignment.viewmodel.CommentListViewModel;

import java.util.Objects;

public class IssueDetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private CommentListViewModel commentListViewModel;
    private CommentDataAdapter commentDataAdapter;
    private Issue currentIssue;
    private TextView tvDescription;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        commentListViewModel = ViewModelProviders.of(this).get(CommentListViewModel.class);
        init();
    }

    private void init() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Check whether the intent has Issue data
        if (getIntent().hasExtra(Constants.INTENT_EXTRA_ISSUE)) {
            currentIssue = (Issue) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_ISSUE);
        }

        RecyclerView recyclerView = binding.rvCommentList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        commentDataAdapter = new CommentDataAdapter();
        recyclerView.setAdapter(commentDataAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        initAnimation();

        //set the issue data passed from issue listing screen
        binding.issueLayout.setIssue(currentIssue);
        tvDescription = binding.issueLayout.tvDescription;
        tvDescription.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.MAX_VALUE)});
        tvDescription.setMaxLines(Constants.DESCRIPTION_MIN_LINES);

        binding.ivExpand.setOnClickListener((view) -> new Handler().post(() -> {
            // if description text is expanded , then press to collapse the text
            if (tvDescription.getMaxLines() == Integer.MAX_VALUE) {
                tvDescription.setMovementMethod(null);
                tvDescription.setMaxLines(Constants.DESCRIPTION_MIN_LINES);
                collapseAnimation();
            }
            // if description text is collapsed , then press to expand the text
            else {
                tvDescription.setMovementMethod(new ScrollingMovementMethod());
                tvDescription.setMaxLines(Integer.MAX_VALUE);
                expandAnimation();
            }
        }));


        commentListViewModel.getCommentList(currentIssue.getNumber()).observe(this, comments -> {
            commentDataAdapter.setCommentList(comments);
            binding.tvNoData.setVisibility(comments.isEmpty() ? View.VISIBLE : View.GONE);
            binding.rvCommentList.setVisibility(comments.isEmpty() ? View.GONE : View.VISIBLE);
        });
    }

    /**
     * Initialize the animation to the expand and collapse when issue description text is clicked
     */
    private void initAnimation() {
        final Guideline guideline = binding.guideline;
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
        float startValue = lp.guidePercent;
        float endValue = 1f;
        valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(valueAnimator -> {
            float value = valueAnimator.getAnimatedFraction();
            if (value >= startValue) {
                ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
                lp1.guidePercent = valueAnimator.getAnimatedFraction();
                guideline.setLayoutParams(lp1);
                if (value == endValue) {
                    binding.ivExpand.setImageResource(R.drawable.ic_expand_less);
                } else {
                    binding.ivExpand.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });
    }

    /**
     * expand the issue description text
     */
    private void expandAnimation() {
        valueAnimator.start();
    }

    /**
     * collapse the issue description text
     */
    private void collapseAnimation() {
        valueAnimator.reverse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // when pressed on the back arrow in action bar , call onBackPressed method
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
