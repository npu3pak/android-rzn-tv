package tv.rzn.rzntv.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tv.rzn.rzntv.model.NavigationItem;

/**
 * Автор: EVSafronov
 * Дата: 01.01.15.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<NavigationItem> {
    private LayoutInflater inflater;

    public NavigationDrawerAdapter(Context context, List<NavigationItem> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        NavigationItem item = getItem(position);
        holder.showMenuItem(item);
        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;

        private ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(android.R.id.text1);
        }

        public void showMenuItem(NavigationItem menuItem) {
            textView.setText(menuItem.title);
        }
    }
}
