package tv.rzn.rzntv.util;

import android.content.Context;

import java.util.List;

import tv.rzn.rzntv.model.NavigationItem;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.network.responses.PreviewListResponse;

/**
 * Автор: EVSafronov
 * Дата: 01.01.15.
 */
public interface IDataSource {
    void getPreviewItemsAsync(NavigationItem menuItem, Long lastKnownId, Long page, boolean useCache, PreviewItemsCallback callback);

    void getItemContentAsync(PreviewItem item, ItemContentCallback callback);

    List<NavigationItem> getMenuItems();

    void start(Context context);

    void stop();

    interface PreviewItemsCallback {
        void onSuccess(PreviewListResponse response);
        void onFail(Exception exception);
    }

    interface ItemContentCallback {
        void onSuccess(String content);
        void onFail(Exception exception);
    }

    interface IDataSourceContainer {
        IDataSource getDataSource();
    }
}
