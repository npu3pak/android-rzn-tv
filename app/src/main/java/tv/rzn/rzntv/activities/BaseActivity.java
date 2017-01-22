package tv.rzn.rzntv.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import tv.rzn.rzntv.util.IDataSource;
import tv.rzn.rzntv.util.MockDataSource;
import tv.rzn.rzntv.util.NetworkDataSource;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public abstract class BaseActivity extends ActionBarActivity implements IDataSource.IDataSourceContainer {
    private IDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new NetworkDataSource();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.NONE)

                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataSource.start(this);
    }

    @Override
    protected void onStop() {
        dataSource.stop();
        super.onStop();
    }

    @Override
    public IDataSource getDataSource() {
        return dataSource;
    }
}
