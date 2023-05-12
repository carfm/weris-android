package sv.com.weris.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hirondelle.date4j.DateTime;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static sv.com.weris.util.AppConstants.DATETIME_FORMAT_API;
import static sv.com.weris.util.AppConstants.DATETIME_FORMAT_TAG;
import static sv.com.weris.util.AppConstants.DATE_FORMAT_API;


public class UtilAPI {

    public static Gson getGsonSync(){
        return new GsonBuilder()
                //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapter(DateTime.class, serializerDateTime)
                .registerTypeAdapter(DateTime.class, deserializerDateTime)
                .serializeNulls()
                .create();
    }


    private static JsonSerializer<DateTime> serializerDateTime = new JsonSerializer<DateTime>() {
        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext
                context) {

            if(src.hasHourMinuteSecond()){
                return src == null ? null : new JsonPrimitive((src.format(DATETIME_FORMAT_API)));
            }else{
                return src == null ? null : new JsonPrimitive((src.format(DATE_FORMAT_API)));
            }
        }
    };

    private static JsonDeserializer<DateTime> deserializerDateTime = new JsonDeserializer<DateTime>() {
        @Override
        public DateTime deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext
                context) {

            if(json.getAsString() == null || json.getAsString().isEmpty()){
                return  null;
            }

            try {
                Date dateAPI = new SimpleDateFormat(DATETIME_FORMAT_API).parse(json.getAsString());
                SimpleDateFormat a = new SimpleDateFormat(DATETIME_FORMAT_TAG);
                a.setLenient(false);
                return new DateTime(a.format(dateAPI));
            } catch (ParseException e) {
                return null;
            }
        }
    };

    public static RequestBody createPartFromString(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

}
