package tv.rzn.rzntv.network;

import android.app.Application;

import com.octo.android.robospice.okhttp.OkHttpSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

/**
 * Автор: EVSafronov
 * Дата: 05.01.15.
 */
public class PlainTextSpiceService extends OkHttpSpiceService {
    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        InFileStringObjectPersister persister = new InFileStringObjectPersister(getApplication());
        cacheManager.addPersister(persister);
        return cacheManager;
    }
}
