package com.jaagro.crm.biz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaagro.crm.api.dto.response.gaodemap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import okhttp3.OkHttpClient;

/**
 * 高德地图
 *
 * @author Gavin
 *
 */
@Component
public class GaoDeMapUtil {
    private static final Logger logger = LoggerFactory.getLogger(GaoDeMapUtil.class);

    /**
     * 高德应用的地址
     */
    private static String gaodeAppID = "8ba5378ec9d077349825255cf48b66d2";
    /**
     * 高德应用的地址
     */
//    private static String map_codeurl = "http://restapi.amap.com/v3/geocode/geo?parameters";
    private static String map_codeurl = "http://restapi.amap.com/v3/geocode/geo";
    /**
     * 输入地址返回识别后的信息
     *
     * @param address
     * @return 返回的类gaodelocation，详见类
     */
    public GaodeLocation  getLocatoin(String address) {
        GaodeLocation location = null;

        if (address != null) {
            try {
                location = new GaodeLocation();
                String url = map_codeurl.replace("parameters", "");
                String params = "key=" + gaodeAppID + "&address=" + address;

                logger.info("高德地图params:" + params);
                Map<String, String> param = new HashMap<>();

                param.put("key",gaodeAppID);
                param.put("address",address);
//                String result =HttpClientUtil.doPost(url, param);
                String result = HttpClientUtil.httpPost(url, params);
                logger.info("高德地图返回结果:" + result);
                JSONObject jsonObject = JSONObject.parseObject(result);

                // 解析json
                location.setStatus(jsonObject.getString("status"));
                location.setInfo(jsonObject.getString("info"));
                location.setCount(jsonObject.getString("count"));

                List<Geocodes> geoCodes = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
                // 遍历解析出来的结果
                if ((jsonArray != null) && (jsonArray.size() > 0)) {
                    JSONObject jo = (JSONObject) jsonArray.get(0);
                    Geocodes go = new Geocodes();
                    go.setFormatted_address(jo.getString("formatted_address"));
                    go.setProvince(jo.getString("province"));
                    go.setCitycode(jo.getString("citycode"));
                    go.setCity(jo.getString("city"));
                    go.setDistrict(jo.getString("district"));
                    // 地址所在的乡镇
//                    JSONArray ts = jo.getJSONArray("township");
//                    if (ts != null && ts.size() > 0) {
//                        go.setTownship(ts.getString(0));
//                    }
                    // 地址编号
//                    go.setAdcode(jo.getString("adcode"));
//                    // 街道
//                    JSONArray jd = jo.getJSONArray("street");
//                    if (jd != null && jd.size() > 0) {
//                        go.setStreet(jd.getString(0));
//                    }
                    // 号码
//                    JSONArray hm = jo.getJSONArray("number");
//                    if (hm != null && hm.size() > 0) {
//                        go.setStreet(hm.getString(0));
//                    }
                    go.setLocation(jo.getString("location"));
                    go.setLevel(jo.getString("level"));
                    geoCodes.add(go);
                }
                location.setGeocodes(geoCodes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public static void main(String[] args) {
        GaoDeMapUtil gdm = new GaoDeMapUtil();
        GaodeLocation result = gdm.getLocatoin("槐古豪庭");
        System.out.println(JSON.toJSONString(result));
    }
}
