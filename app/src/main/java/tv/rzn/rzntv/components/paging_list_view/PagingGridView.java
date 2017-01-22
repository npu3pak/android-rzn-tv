package tv.rzn.rzntv.components.paging_list_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import java.util.List;

import tv.rzn.rzntv.components.GridViewWithHeaderAndFooter;


public class PagingGridView extends GridViewWithHeaderAndFooter implements LoadingErrorView.ILoadingErrorDelegate {

    public interface PagingEventsListener {
        void onLoadMoreItems(boolean useCache);
    }

    private boolean isLoading;
    private boolean hasMoreItems;
    private PagingEventsListener pagingEventsListener;
    private LoadingView loadingView;
    private LoadingErrorView errorView;

    private OnScrollListener onScrollListener;

    public PagingGridView(Context context) {
        super(context);
        init();
    }

    public PagingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagingGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPagingEventsListener(PagingEventsListener pagingEventsListener) {
        this.pagingEventsListener = pagingEventsListener;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        if (!this.hasMoreItems) {
            removeFooterView(loadingView);
        }
        if (this.errorView != null) {
            removeFooterView(errorView);
            errorView = null;
        }
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }


    public void onFinishLoading(boolean hasMoreItems, List<? extends Object> newItems) {
        setHasMoreItems(hasMoreItems);
        setIsLoading(false);
        if (newItems != null && newItems.size() > 0) {
            ListAdapter adapter = ((HeaderViewGridAdapter) getAdapter()).getWrappedAdapter();
            if (adapter instanceof PagingBaseAdapter) {
                ((PagingBaseAdapter) adapter).addMoreItems(newItems);
            }
        }
    }

    public void onErrorLoading() {
        onFinishLoading(false, null);

        errorView = new LoadingErrorView(getContext(), this);
        addFooterView(errorView);
    }

    @Override
    public void onRetryButtonClick() {
        reloadData();
    }

    public void reloadData() {
        if (errorView != null) {
            removeFooterView(errorView);
            errorView = null;
        }
        removeFooterView(loadingView);
        loadingView = new LoadingView(getContext());
        addFooterView(loadingView);

        if (pagingEventsListener != null) {
            isLoading = true;
            pagingEventsListener.onLoadMoreItems(false);
        }
    }


    private void init() {
        isLoading = false;
        loadingView = new LoadingView(getContext());
        addFooterView(loadingView);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                if (totalItemCount > 0) {
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount)) {
                        if (pagingEventsListener != null) {
                            isLoading = true;
                            pagingEventsListener.onLoadMoreItems(true);
                        }

                    }
                }
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }
}
