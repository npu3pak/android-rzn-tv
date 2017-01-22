package tv.rzn.rzntv.network;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Автор: EVSafronov
 * Дата: 31.12.14.
 */
public class JsonSpiceService extends RetrofitGsonSpiceService{

    public static final String SERVICE_URL = "http://rzn.tv/m";
    public static final String DATE_FORMAT = "yyyy-MM-dd kk:mm:ss";

    @Override
    protected String getServerUrl() {
        return SERVICE_URL;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(NetApi.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        final RestAdapter.Builder builder = super.createRestAdapterBuilder();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        GsonConverter converter = new GsonConverter(gsonBuilder.create());
        builder.setConverter(converter);
        return builder;
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement == null || jsonElement.isJsonNull() || jsonElement.getAsString().equals(""))
                return null;
            String stringValue = jsonElement.getAsString();
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dateFormat.parse(stringValue);
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
