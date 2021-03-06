package tv.rzn.rzntv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import tv.rzn.rzntv.R;
import tv.rzn.rzntv.components.PreviewListAdapter;
import tv.rzn.rzntv.components.paging_list_view.PagingListView;
import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.NavigationItem;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.network.responses.PreviewListResponse;
import tv.rzn.rzntv.util.IDataSource;


public class PreviewListFragment extends Fragment implements AdapterView.OnItemClickListener, PagingListView.PagingEventsListener {

    private static final String ARG_NAVIGATION_ITEM = "navigation_item";

    private static final String STATE_KEY_NAVIGATION_ITEM = "navigation_item";
    private static final String STATE_KEY_ITEMS = "Items";
    private static final String STATE_LIST_VIEW = "Position";
    private static final String STATE_KEY_PAGE_ID = "PageId";

    private NavigationItem selectedNavigationItem;

    private PreviewListAdapter listAdapter;
    private PagingListView listView;

    private IPreviewFragmentDelegate delegate;

    private long itemsPage = 1;


    public static PreviewListFragment newInstance(NavigationItem navigationItem) {
        PreviewListFragment fragment = new PreviewListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NAVIGATION_ITEM, navigationItem);
        fragment.setArguments(args);
        return fragment;
    }

    public PreviewListFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_preview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        itemsPage = 1;
        if (listAdapter != null) {
            listAdapter.removeAllItems();
        }
        if (listView != null) {
            listView.reloadData();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        selectedNavigationItem = getArguments().getParcelable(ARG_NAVIGATION_ITEM);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            delegate = (IPreviewFragmentDelegate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IPreviewFragmentDelegate");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((IPreviewFragmentDelegate) getActivity()).updateTitle(selectedNavigationItem.title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            itemsPage = savedInstanceState.getLong(STATE_KEY_PAGE_ID);
            selectedNavigationItem = savedInstanceState.getParcelable(STATE_KEY_NAVIGATION_ITEM);
            ArrayList<PreviewItem> items = savedInstanceState.getParcelableArrayList(STATE_KEY_ITEMS);
            listAdapter = new PreviewListAdapter(getActivity(), getDetailsLayoutId(selectedNavigationItem.contentType), items, selectedNavigationItem.contentType);
        } else {
            listAdapter = new PreviewListAdapter(getActivity(), getDetailsLayoutId(selectedNavigationItem.contentType), selectedNavigationItem.contentType);
        }

        View view = inflater.inflate(R.layout.fragment_preview_list, container, false);
        listView = (PagingListView) view.findViewById(R.id.preview_list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        listView.setPagingEventsListener(this);

        if (savedInstanceState != null) {
            Parcelable listViewState = savedInstanceState.getParcelable(STATE_LIST_VIEW);
            listView.onRestoreInstanceState(listViewState);
        }

        listView.setHasMoreItems(true);
        return view;
    }

    private int getDetailsLayoutId(ContentType contentType) {
        switch (contentType) {
            case TEXT_LIST:
                return R.layout.preview_item_text;
            case TEXT_IMAGE_LIST:
                return R.layout.preview_item_text_image;
            case IMAGE_GRID:
                return R.layout.preview_item_grid;
            case VIDEO:
                return R.layout.preview_item_video;
            case VIDEO_WITHOUT_PREVIEW:
                return R.layout.preview_item_text;
            default:
                return R.layout.preview_item_text;
        }
    }

    @Override
    public void onLoadMoreItems(boolean useCache) {
        getDataSource().getPreviewItemsAsync(selectedNavigationItem, getLastKnownId(), itemsPage, useCache, new IDataSource.PreviewItemsCallback() {
            @Override
            public void onSuccess(PreviewListResponse response) {
                final boolean hasMoreItems = response != null && response.nextPageUrl != null && !response.nextPageUrl.isEmpty();
                if (response != null && response.previewItems != null) {
                    listView.onFinishLoading(hasMoreItems, response.previewItems);
                    itemsPage++;
                }
            }

            @Override
            public void onFail(Exception exception) {
                listView.onErrorLoading();
            }
        });
    }

    private Long getLastKnownId() {
        if (listAdapter != null && listAdapter.getCount() > 0) {
            PreviewItem lastItem = listAdapter.getItem(listAdapter.getCount() - 1);
            return lastItem.id;
        }
        return null;
    }

    private IDataSource getDataSource() {
        return ((IDataSource.IDataSourceContainer) getActivity()).getDataSource();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_KEY_NAVIGATION_ITEM, selectedNavigationItem);
        ArrayList<PreviewItem> items = new ArrayList<>();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            PreviewItem item = listAdapter.getItem(i);
            items.add(item);
        }
        outState.putLong(STATE_KEY_PAGE_ID, itemsPage);
        outState.putParcelableArrayList(STATE_KEY_ITEMS, items);
        outState.putParcelable(STATE_LIST_VIEW, listView.onSaveInstanceState());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        delegate = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != delegate) {
            PreviewItem previewItem = listAdapter.getItem(position);
            delegate.showPreviewItemContent(previewItem, selectedNavigationItem);
        }
    }

    public interface IPreviewFragmentDelegate {
        void showPreviewItemContent(PreviewItem previewItem, NavigationItem menuItem);

        void updateTitle(String title);
    }
}
