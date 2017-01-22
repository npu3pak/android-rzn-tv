package tv.rzn.rzntv.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import tv.rzn.rzntv.R;
import tv.rzn.rzntv.fragments.WebContentFragment;
import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.util.IDataSource;

public class ContentActivity extends BaseActivity implements IDataSource.IDataSourceContainer {
    public static final String INTENT_KEY_CONTENT_TYPE = "ContentType";
    public static final String INTENT_KEY_SELECTED_ITEM = "SelectedItem";

    private ContentType contentType;
    private PreviewItem previewItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contentType = (ContentType) getIntent().getSerializableExtra(INTENT_KEY_CONTENT_TYPE);
        previewItem = getIntent().getParcelableExtra(INTENT_KEY_SELECTED_ITEM);

        setContentView(R.layout.activity_content);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, WebContentFragment.getInstance(previewItem))
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open_in_browser) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(previewItem.siteUrl));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
