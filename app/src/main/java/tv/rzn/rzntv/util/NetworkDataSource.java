package tv.rzn.rzntv.util;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.NavigationItem;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.network.JsonSpiceService;
import tv.rzn.rzntv.network.PlainTextSpiceService;
import tv.rzn.rzntv.network.requests.ItemContentRequest;
import tv.rzn.rzntv.network.requests.PreviewListRequest;
import tv.rzn.rzntv.network.responses.PreviewListResponse;

/**
 * Автор: EVSafronov
 * Дата: 01.01.15.
 */
public class NetworkDataSource implements IDataSource {
    private SpiceManager spiceManagerJson = new SpiceManager(JsonSpiceService.class);
    private SpiceManager spiceManagerPlainText = new SpiceManager(PlainTextSpiceService.class);

    @Override
    public void getPreviewItemsAsync(NavigationItem menuItem, Long lastKnownId, Long page, boolean useCache, final PreviewItemsCallback callback) {
        PreviewListRequest request = new PreviewListRequest(menuItem.requestPath, lastKnownId, page);
        long cacheDuration = useCache ? DurationInMillis.ONE_HOUR : DurationInMillis.ALWAYS_EXPIRED;

        spiceManagerJson.execute(request, request.hashCode(), cacheDuration, new RequestListener<PreviewListResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                callback.onFail(spiceException);
            }

            @Override
            public void onRequestSuccess(PreviewListResponse previewListResponse) {
                callback.onSuccess(previewListResponse);
            }
        });
    }

    @Override
    public void getItemContentAsync(PreviewItem item, final ItemContentCallback callback) {
        ItemContentRequest request = new ItemContentRequest(item);
        spiceManagerPlainText.execute(request, request.hashCode(), DurationInMillis.ALWAYS_RETURNED, new RequestListener<String>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                callback.onFail(spiceException);
            }

            @Override
            public void onRequestSuccess(String content) {
                callback.onSuccess(content);
            }
        });
    }

    @Override
    public List<NavigationItem> getMenuItems() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("Новости", 1, ContentType.TEXT_IMAGE_LIST, "news", null));
        items.add(new NavigationItem("Статьи", 2, ContentType.TEXT_IMAGE_LIST, "articles", null));
        items.add(new NavigationItem("Интервью", 3, ContentType.TEXT_IMAGE_LIST, "talks", null));
        items.add(new NavigationItem("Анонсы", 4, ContentType.IMAGE_GRID, "announcements", null));
        items.add(new NavigationItem("Видео новости", 5, ContentType.VIDEO_WITHOUT_PREVIEW, "video", null));
        items.add(new NavigationItem("Видео интервью", 6, ContentType.VIDEO_WITHOUT_PREVIEW, "video_int", null));
        return items;
    }

    @Override
    public void start(Context context) {
        spiceManagerPlainText.start(context);
        spiceManagerJson.start(context);
    }

    @Override
    public void stop() {
        spiceManagerPlainText.shouldStop();
        spiceManagerJson.shouldStop();
    }
}
