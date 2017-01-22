package tv.rzn.rzntv.components.paging_list_view;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;


public abstract class PagingBaseAdapter<T> extends ArrayAdapter<T> {

    protected PagingBaseAdapter(Context context, int resource) {
        super(context, resource);
    }

    protected PagingBaseAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    public void addMoreItems(List<T> newItems) {
        for(T t : newItems){
            super.add(t);
        }
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        clear();
        notifyDataSetChanged();
    }
}
