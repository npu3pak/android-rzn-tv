package tv.rzn.rzntv.components.paging_list_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import tv.rzn.rzntv.R;


public class LoadingErrorView extends LinearLayout implements View.OnClickListener {

    private ILoadingErrorDelegate delegate;

    public LoadingErrorView(Context context, ILoadingErrorDelegate delegate) {
        super(context);
        init();
        this.delegate = delegate;
    }

    public LoadingErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingErrorView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_error_view, this);
        Button retryButton = (Button) findViewById(R.id.loading_error_button_retry);
        retryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.delegate.onRetryButtonClick();
    }

    public static interface ILoadingErrorDelegate {
        void onRetryButtonClick();
    }
}
