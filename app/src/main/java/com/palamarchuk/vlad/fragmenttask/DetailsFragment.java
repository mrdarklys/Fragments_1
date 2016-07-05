package com.palamarchuk.vlad.fragmenttask;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by User on 25.06.2016.
 */
public class DetailsFragment extends Fragment {
        WebView teamDescr;
    private ProgressBar mProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent launchigIntent = getActivity().getIntent();
        int teamName = launchigIntent.getIntExtra("teamPos", 0);
        ViewGroup view = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.viewer, container);
        teamDescr = (WebView) view.findViewById(R.id.webView);
        mProgress = (ProgressBar) view.findViewById(R.id.progress_web_loading);
        mProgress.setMax(100);
        view.setLayoutTransition(new LayoutTransition());
        updateTeamDescr(teamName);
        return view;

    }
    public class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    public void updateTeamDescr(int name) {
        teamDescr.getSettings().setJavaScriptEnabled(true);
        /*teamDescr.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mProgress, "progress" , 0, newProgress);
                animator.setDuration(1000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                if (newProgress == mProgress.getMax()) {
                    mProgress.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setVisibility(View.GONE);
                        }
                    }, 500);

                }
                super.onProgressChanged(view, newProgress);
            }

        });*/
        teamDescr.loadUrl(Teams.mTeamUrlArray[name]);
        teamDescr.setWebViewClient(new MyWebViewClient() {



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ObjectAnimator animator = ObjectAnimator.ofInt(mProgress, "progress" , 0, 100);
                animator.setDuration(2000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               if(mProgress.getMax() == 100){
                mProgress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setVisibility(View.GONE);
                    }
                }, 500);
               }
                super.onPageFinished(view, url);
            }
        });


        teamDescr.setBackgroundColor(Color.LTGRAY);

    }
}
