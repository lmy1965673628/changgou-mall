package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Brand的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 根据分类id查询品牌集合
     * @param categoryId
     * @return
     */
    @Select("SELECT tb.* FROM tb_category_brand AS tcb, tb_brand AS tb WHERE tb.id = tcb.brand_id AND tcb.category_id=#{categoryId}")
    List<Brand> findByCategory(@Param("categoryId") Integer categoryId);
}
