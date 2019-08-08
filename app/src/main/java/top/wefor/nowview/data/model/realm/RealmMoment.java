package top.wefor.nowview.data.model.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import top.wefor.nowview.data.model.entity.Moment;

/**
 * Created by ice on 16/4/13 10:56.
 */
public class RealmMoment extends RealmObject implements AbsNowRealmObject<Moment> {
    @PrimaryKey
    @Required
    public String pk;
    @SerializedName("url")
    public String url;
    @SerializedName("title")
    public String title;
    @SerializedName("imgUrls")
    public String imgUrls;
    @SerializedName("content")
    public String content;

    @Override
    public Moment toEntity() {
        Moment moment = new Moment();
        moment.url = url;
        moment.title = title;
        moment.imgUrls = imgUrls;
        moment.content = content;
        return moment;
    }

    @Override
    public void setFromEntity(Moment moment) {
        url = moment.url;
        title = moment.title;
        imgUrls = moment.imgUrls;
        content = moment.content;
        pk = url + title;
    }

}
