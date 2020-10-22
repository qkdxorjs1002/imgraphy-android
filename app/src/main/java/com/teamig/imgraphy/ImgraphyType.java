package com.teamig.imgraphy;

import com.google.gson.annotations.SerializedName;

public class ImgraphyType {

    public static class Graphy {

        @SerializedName("uuid")
        String uuid;

        @SerializedName("date")
        long date;

        @SerializedName("ext")
        String ext;

        @SerializedName("tag")
        String tag;

        @SerializedName("favcnt")
        int favcnt;

        @SerializedName("shrcnt")
        int shrcnt;

        @SerializedName("license")
        int license;

        @SerializedName("uploader")
        String uploader;

        @SerializedName("deprec")
        boolean deprec;
    }

    public static class Options {

        int count_per_page, page;
        String keyword;

        public Options(int count_per_page, int page) {
            this.count_per_page = count_per_page;
            this.page = page;
            this.keyword = "";
        }

        public Options(int count_per_page, int page, String keyword) {
            this.count_per_page = count_per_page;
            this.page = page;
            this.keyword = keyword;
        }
    }
}
