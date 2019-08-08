package top.wefor.nowview.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import top.wefor.nowview.data.model.entity.Gank;

/**
 * Created on 16/7/4.
 *
 * @author ice
 */
public class GankMeizhiResult extends BaseResult {


    @SerializedName("count")
    public int count;

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public List<Gank> results;

}
