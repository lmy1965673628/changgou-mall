package com.changgou.search.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/search")
@CrossOrigin
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 导入数据
     */
    @GetMapping("/import")
    public Result search() {
        skuService.importEs();
        return new Result(true, StatusCode.OK, "导入数据到索引库中成功！");
    }

    /**
     * @param searchMap 搜索的条件 map
     * @return resultMap  返回的结果 map
     */
    @GetMapping
    public Map search(@RequestParam(required = false) Map searchMap) {
        return skuService.search(searchMap);
    }
}
