package tv.rzn.rzntv.network.requests;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import tv.rzn.rzntv.network.NetApi;
import tv.rzn.rzntv.network.responses.PreviewListResponse;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public class PreviewListRequest extends RetrofitSpiceRequest<PreviewListResponse, NetApi> {
    private static final int ELEMENTS_COUNT = 10;

    private String pathSuffix;
    private Long lastKnownId;
    private Long pageId;

    public PreviewListRequest(String pathSuffix, Long lastKnownId, Long page) {
        super(PreviewListResponse.class, NetApi.class);
        this.pathSuffix = pathSuffix;
        this.lastKnownId = lastKnownId;
        this.pageId = page;
    }

    @Override
    public PreviewListResponse loadDataFromNetwork() throws Exception {
        return getService().getPreviewList(pathSuffix, lastKnownId, "json", pageId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreviewListRequest request = (PreviewListRequest) o;

        //noinspection AccessStaticViaInstance,ConstantConditions
        if (ELEMENTS_COUNT != request.ELEMENTS_COUNT) return false;
        if (lastKnownId != null ? !lastKnownId.equals(request.lastKnownId) : request.lastKnownId != null)
            return false;
        if (pageId != null ? !pageId.equals(request.pageId) : request.pageId != null)
            return false;
        if (pathSuffix != null ? !pathSuffix.equals(request.pathSuffix) : request.pathSuffix != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ELEMENTS_COUNT;
        result = 31 * result + (pageId != null ? pageId.hashCode() : 0);
        result = 31 * result + (pathSuffix != null ? pathSuffix.hashCode() : 0);
        result = 31 * result + (lastKnownId != null ? lastKnownId.hashCode() : 0);
        return result;
    }
}
