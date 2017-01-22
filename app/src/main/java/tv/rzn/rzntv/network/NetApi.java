package tv.rzn.rzntv.network;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import tv.rzn.rzntv.network.responses.PreviewListResponse;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public interface NetApi {
    @GET("/{path}")
    PreviewListResponse getPreviewList(
            @Path("path") String path,
            @Query("last_known_id") Long lastKnownId,
            @Query("format") String format,
            @Query("page") Long page
    );
}
