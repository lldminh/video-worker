package com.golftec.teaching.model.data;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrillCategoryResponseData {

    @SerializedName("swing_type_list")
    public List<DrillSwingType> swing_type_list = Lists.newArrayList();

    @SerializedName("category_list")
    public List<DrillCategory> category_list = Lists.newArrayList();
}
