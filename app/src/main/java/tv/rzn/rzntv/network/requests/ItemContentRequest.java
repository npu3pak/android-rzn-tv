package tv.rzn.rzntv.network.requests;

import android.net.Uri;

import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import com.squareup.okhttp.OkUrlFactory;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import tv.rzn.rzntv.model.PreviewItem;

/**
 * Автор: EVSafronov
 * Дата: 05.01.15.
 */
public class ItemContentRequest extends OkHttpSpiceRequest<String> {
    private PreviewItem previewItem;

    public ItemContentRequest(PreviewItem previewItem) {
        super(String.class);
        this.previewItem = previewItem;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {
        Uri.Builder uriBuilder = Uri.parse(previewItem.contentUrl).buildUpon();
        URI uri = new URI(uriBuilder.build().toString());
        OkUrlFactory urlFactory = new OkUrlFactory(getOkHttpClient());
        HttpURLConnection connection = urlFactory.open(uri.toURL());
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            return IOUtils.toString(in, "utf-8");
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemContentRequest request = (ItemContentRequest) o;

        if (previewItem != null ? !previewItem.equals(request.previewItem) : request.previewItem != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return previewItem != null ? previewItem.hashCode() : 0;
    }
}
