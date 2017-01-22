package tv.rzn.rzntv.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tv.rzn.rzntv.model.PreviewItem;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public class PreviewListResponse {
    @SerializedName("results")
    public List<PreviewItem> previewItems;
    @SerializedName("next")
    public String nextPageUrl;
}
