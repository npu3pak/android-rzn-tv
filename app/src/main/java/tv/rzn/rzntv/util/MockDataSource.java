package tv.rzn.rzntv.util;

import android.content.Context;
import android.os.AsyncTask;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.NavigationItem;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.network.PlainTextSpiceService;
import tv.rzn.rzntv.network.requests.ItemContentRequest;
import tv.rzn.rzntv.network.responses.PreviewListResponse;

/**
 * Автор: EVSafronov
 * Дата: 01.01.15.
 */
public class MockDataSource implements IDataSource {
    private SpiceManager spiceManagerPlainText = new SpiceManager(PlainTextSpiceService.class);

    @Override
    public void getPreviewItemsAsync(final NavigationItem menuItem, Long lastKnownId, Long page, boolean useCache, final PreviewItemsCallback callback) {
        new AsyncTask<Void, Void, PreviewListResponse>() {
            @Override
            protected PreviewListResponse doInBackground(Void... params) {
                PreviewItem item = new PreviewItem();
                switch (menuItem.contentType) {

                    case TEXT_LIST:
                    case TEXT_IMAGE_LIST:
                        item.id = 0;
                        item.title = "Игорь Овчинников: «Во взаимодействии со зрителем российские театры не делают даже 20% того, что уже придумано».";
                        item.description = "Актер, помощник художественного руководителя в Мастерской Петра Фоменко о «формуле любви», менеджменте, региональных театрах и всемирной паутине.";
                        item.timeStamp = new Date();
                        item.imageUrl = "http://rzn.tv/media/news/роман_потапов.jpg";
                        item.contentUrl = "http://rzn.tv/talks/2014/11/30/35/";
                        item.siteUrl = "http://rzn.tv/talks/2014/11/30/35/";
                        break;
                    case IMAGE_GRID:
                        item.id = 0;
                        item.title = "«Новогоднее путешествие вокруг света»";
                        item.description = "Театр кукол";
                        item.timeStamp = new Date();
                        item.imageUrl = "http://rzn.tv/media/news/новогоднее_путешествие_3.jpg";
                        item.contentUrl = "http://rzn.tv/announcements/2015/01/02/4059/";
                        item.siteUrl = "http://rzn.tv/announcements/2015/01/02/4059/";
                        break;
                    case VIDEO:
                        item.id = 0;
                        item.title = "С Новым годом!";
                        item.description = "Рязань, с наступающим!";
                        item.timeStamp = new Date();
                        item.imageUrl = "http://rzn.tv/media/news/роман_потапов.jpg";
                        item.contentUrl = "http://www.youtube.com/embed/mZUzpPCoN4k";
                        item.siteUrl = "http://rzn.tv/video_story/45/";
                        break;
                }
                List<PreviewItem> items = new ArrayList<>();
                items.add(item);
                if (menuItem.contentType == ContentType.TEXT_IMAGE_LIST) {
                    item = new PreviewItem();
                    item.id = 1;
                    item.imageUrl = "http://rzn.tv/media/news/снеговики.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 2;
                    item.imageUrl = "http://rzn.tv/media/news/новый_год_шагает_по_планете.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 3;
                    item.imageUrl = "http://rzn.tv/media/news/Новый_год_часики.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 4;
                    item.imageUrl = "http://rzn.tv/media/news/метель.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 5;
                    item.imageUrl = "http://rzn.tv/media/news/новый_год_подарки.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 6;
                    item.imageUrl = "http://rzn.tv/media/news/новый_год_платье_елка.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 7;
                    item.imageUrl = "http://rzn.tv/media/news/овца.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 8;
                    item.imageUrl = "http://rzn.tv/media/news/Новый_год_дома.jpg.235x0_q85.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 9;
                    item.imageUrl = "http://rzn.tv/media/news/аварийное_жилье123.jpg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 10;
                    item.imageUrl = "http://rzn.tv/media/news/белорусский_репер_homie.jpeg";
                    items.add(item);
                    item = new PreviewItem();
                    item.id = 11;
                    item.imageUrl = "http://rzn.tv/media/news/светофор123.jpg";
                    items.add(item);
                }

                PreviewListResponse response = new PreviewListResponse();
                response.previewItems = items;
                return response;
            }

            @Override
            protected void onPostExecute(PreviewListResponse previewListResponse) {
                super.onPostExecute(previewListResponse);

                if (Math.random() > 0.1) {
                    callback.onSuccess(previewListResponse);
                } else {
                    callback.onFail(new Exception("Загрузка не удалась"));
                }
            }
        }.execute();
    }

    @Override
    public List<NavigationItem> getMenuItems() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("Список", 1, ContentType.TEXT_LIST, null, null));
        items.add(new NavigationItem("Список с картинками", 2, ContentType.TEXT_IMAGE_LIST, null, null));
        items.add(new NavigationItem("Сетка", 3, ContentType.IMAGE_GRID, null, null));
        items.add(new NavigationItem("Видео", 4, ContentType.VIDEO, null, null));
        return items;
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
    public void start(Context context) {
        spiceManagerPlainText.start(context);
    }

    @Override
    public void stop() {
        spiceManagerPlainText.shouldStop();
    }
}
