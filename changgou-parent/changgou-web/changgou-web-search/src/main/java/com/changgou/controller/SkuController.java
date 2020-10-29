package com.changgou.controller;

import com.changgou.entity.Page;
import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SkuController {
    @Autowired
    private SkuFeign skuFeign;

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap, ModelMap model) {
        Map resultmap = skuFeign.search(searchMap);
        model.addAttribute("result", resultmap);
        //搜索条件
        model.addAttribute("searchMap", searchMap);

        String Url = Url(searchMap);
        model.addAttribute("url", Url);
        //创建一个分页的对象  可以获取当前页 和总个记录数和显示的页码(以当前页为中心的5个页码)
        Page<SkuInfo> infoPage = new Page<>(
                Long.valueOf(resultmap.get("total").toString()),
                Integer.valueOf(resultmap.get("pageNum").toString()),
                Integer.valueOf(resultmap.get("pageSize").toString())
        );
        model.addAttribute("page", infoPage);
        return "search";
    }

    private String Url(Map<String, String> searchMap) {
        String url = "/search/list";
        if (searchMap != null && searchMap.size() > 0) {
            url += "?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {

                if (entry.getKey().equalsIgnoreCase("pageNum")) {
                    continue;
                }

                if (entry.getKey().equals("sortField") || entry.getKey().equals("sortRule")) {
                    continue;
                }

                url += entry.getKey() + "=" + entry.getValue() + "&";
            }
            url = url.substring(0, url.length() - 1);
            if (url.lastIndexOf("&") != -1) {
                url = url.substring(0, url.lastIndexOf("&"));
            }
        }
        return url;
    }
}
