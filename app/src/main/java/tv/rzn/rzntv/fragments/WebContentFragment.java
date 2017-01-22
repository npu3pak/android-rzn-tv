package tv.rzn.rzntv.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import tv.rzn.rzntv.R;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.util.IDataSource;

/**
 * Автор: EVSafronov
 * Дата: 05.01.15.
 */
public class WebContentFragment extends Fragment {
    public static final String ARG_PREVIEW_ITEM = "PreviewItem";

    private PreviewItem previewItem;
    private WebView webView;
    private TextView messageView;
    private ProgressWheel progressWheel;
    private View progressLayout;
    private View errorLayout;

    public static WebContentFragment getInstance(PreviewItem previewItem) {
        WebContentFragment fragment = new WebContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PREVIEW_ITEM, previewItem);
        fragment.setArguments(args);
        return fragment;
    }

    public WebContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previewItem = getArguments().getParcelable(ARG_PREVIEW_ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        webView = (WebView) rootView.findViewById(R.id.content_web_view);
        messageView = (TextView) rootView.findViewById(R.id.content_text_message);
        progressLayout = rootView.findViewById(R.id.content_layout_progress);
        errorLayout = rootView.findViewById(R.id.content_layout_error);
        progressWheel = (ProgressWheel) rootView.findViewById(R.id.content_progress_wheel);

        Button retryButton = (Button) rootView.findViewById(R.id.content_button_retry);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        showLoadingIndicator();

        IDataSource dataSource = ((IDataSource.IDataSourceContainer) getActivity()).getDataSource();
        dataSource.getItemContentAsync(previewItem, new IDataSource.ItemContentCallback() {
            @Override
            public void onSuccess(String content) {
                showWebView(content);
            }

            @Override
            public void onFail(Exception exception) {
                showError("Не удалось загрузить данные\nПроверьте сетевое подключение или повторите попытку позже");
            }
        });
    }

    private void showLoadingIndicator() {
        webView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        progressWheel.spin();
    }

    private void showError(String message) {
        messageView.setText(message);
        webView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
        progressWheel.stopSpinning();
    }

    private void showWebView(String content) {
        webView.loadDataWithBaseURL(previewItem.contentUrl, content, "text/html", "UTF-8", null);
        webView.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
        progressWheel.stopSpinning();
    }
}