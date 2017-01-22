package tv.rzn.rzntv.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tv.rzn.rzntv.R;
import tv.rzn.rzntv.components.paging_list_view.PagingBaseAdapter;
import tv.rzn.rzntv.model.ContentType;
import tv.rzn.rzntv.model.PreviewItem;
import tv.rzn.rzntv.util.DateHelper;

/**
 * Автор: EVSafronov
 * Дата: 06.01.15.
 */
public class PreviewListAdapter extends PagingBaseAdapter<PreviewItem> {
    private ContentType contentType;
    private LayoutInflater inflater;
    private int layoutId;

    public PreviewListAdapter(Context context, int resource, ContentType contentType) {
        super(context, resource);
        this.contentType = contentType;
        inflater = LayoutInflater.from(getContext());
        layoutId = resource;
    }

    public PreviewListAdapter(Context context, int resource, List<PreviewItem> objects, ContentType contentType) {
        super(context, resource, objects);
        this.contentType = contentType;
        inflater = LayoutInflater.from(getContext());
        layoutId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(layoutId, null);
        }

        TextView titleTextView = (TextView) view.findViewById(R.id.preview_item_text_title);
        TextView detailsTextView = (TextView) view.findViewById(R.id.preview_item_text_description);
        TextView timestampTextView = (TextView) view.findViewById(R.id.preview_item_text_timestamp);

        PreviewItem item = getItem(position);

        titleTextView.setText(item.title);
        detailsTextView.setText(item.description);

        if (item.startDate != null && item.endDate != null) {
            String start = DateHelper.formatDate(item.startDate);
            String end = DateHelper.formatDate(item.endDate);
            if (start.equals(end)) {
                timestampTextView.setText(start);
            } else {
                timestampTextView.setText(String.format("С %s по %s", start, end));
            }
        } else if (item.startDate != null) {
            String start = DateHelper.formatDate(item.startDate);
            timestampTextView.setText(start);
        } else {
            timestampTextView.setText(getTimeStamp(item));
        }

        if (contentType != ContentType.TEXT_LIST && contentType != ContentType.VIDEO_WITHOUT_PREVIEW && item.imageUrl != null) {
            ImageView iconImageView = (ImageView) view.findViewById(R.id.preview_item_image_icon);
            ImageLoader.getInstance().displayImage(item.imageUrl, iconImageView);

            //Загружаем картинку с помощью Picasso
//            Picasso.with(getContext())
//                    .load(item.imageUrl)
//                    .resize(320, 240)
//                    .centerCrop()
//                    .noFade()
//                    .into(iconImageView);
        }

        return view;
    }

    private String getTimeStamp(PreviewItem item) {
        if (item.timeStamp != null) {
            if (DateHelper.isToday(item.timeStamp)) {
                return "Сегодня";
            } else if (DateHelper.isYesterday(item.timeStamp)) {
                return "Вчера";
            } else {
                return DateHelper.formatDate(item.timeStamp);
            }
        } else {
            return "";
        }
    }
}
