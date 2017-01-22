package tv.rzn.rzntv.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import tv.rzn.rzntv.R;
import tv.rzn.rzntv.fragments.NavigationDrawerFragment;
import tv.rzn.rzntv.fragments.PreviewGridFragment;
import tv.rzn.rzntv.fragments.PreviewListFragment;
import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.NavigationItem;
import tv.rzn.rzntv.model.PreviewItem;

import static tv.rzn.rzntv.fragments.NavigationDrawerFragment.INavigationDrawerDelegate;


public class PreviewActivity extends BaseActivity implements INavigationDrawerDelegate, PreviewListFragment.IPreviewFragmentDelegate, PreviewGridFragment.IPreviewFragmentDelegate {

    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        title = getTitle();
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void updateTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public void showSelectedNavigationItemPreview(NavigationItem navigationItem, boolean fromSavedInstanceState) {
        if (!fromSavedInstanceState) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment previewFragment;

            //На API 16 нормально заработает сетка, так что можно показать ее
            if (navigationItem.contentType == ContentType.IMAGE_GRID && Build.VERSION.SDK_INT >= 16)
                previewFragment = PreviewGridFragment.newInstance(navigationItem);
            else
                previewFragment = PreviewListFragment.newInstance(navigationItem);

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, previewFragment)
                    .commit();
        }
    }

    @Override
    public void showPreviewItemContent(PreviewItem previewItem, NavigationItem menuItem) {
        if (menuItem.contentType == ContentType.VIDEO || menuItem.contentType == ContentType.VIDEO_WITHOUT_PREVIEW) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(previewItem.video));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.putExtra(ContentActivity.INTENT_KEY_CONTENT_TYPE, menuItem.contentType);
            intent.putExtra(ContentActivity.INTENT_KEY_SELECTED_ITEM, (Parcelable) previewItem);
            startActivity(intent);
        }
    }

}
