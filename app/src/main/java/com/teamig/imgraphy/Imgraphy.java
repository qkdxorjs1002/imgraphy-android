package com.teamig.imgraphy;

import android.graphics.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Imgraphy {
    private Graphy[] graphyList;
    private Options options;

    public static class Graphy {
        long date;
        int favcnt, shrcnt, license;
        boolean deprec;
        String uuid, ext, tag, uploader;
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

    public Imgraphy() { }

    public Imgraphy(Options options) {
        setOptions(options);
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public Options getOptions() {
        return options;
    }

    public Graphy[] getList() {
        return graphyList;
    }

    public Graphy[] refreshList() {
        toObject(requestGraphyList(this.options));

        return getList();
    }

    private JSONObject requestGraphyList(Options options) {
        String url = "https://api.novang.tk/imgraphy/api/list.php";
        JSONObject jsonObject = null;
        try {
            jsonObject = new GetJSONTask("GET", url + "?" +
                    "max=" + options.count_per_page + "&" +
                    "page=" + options.page + "&" +
                    "keyword=" + options.keyword).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private void toObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return ;
        }
        try {
            this.graphyList = new Graphy[jsonObject.getInt("count")];
            JSONArray jsonArray = jsonObject.getJSONArray("list");

            for (int idx = 0; idx < this.graphyList.length; idx++) {
                Graphy graphy = new Graphy();
                JSONObject json = (JSONObject) jsonArray.get(idx);

                graphy.uuid = json.getString("uuid");
                graphy.date = json.getLong("date");
                graphy.ext = json.getString("ext");
                graphy.tag = json.getString("tag");
                graphy.favcnt = json.getInt("favcnt");
                graphy.shrcnt = json.getInt("shrcnt");
                graphy.license = json.getInt("license");
                graphy.uploader = json.getString("uploader");
                graphy.deprec = Boolean.parseBoolean(json.getString("deprec"));

                this.graphyList[idx] = graphy;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
