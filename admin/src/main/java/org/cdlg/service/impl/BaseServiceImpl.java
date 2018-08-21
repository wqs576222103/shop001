package org.cdlg.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.cdlg.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 21:18
 * @Description: I LOVE IT？
 */
//为了不让new,写成abstract
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    @Override
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public T queryOneByCondition(T t) {
        return mapper.selectOneByExample(t);
    }

    @Override
    public List<T> queryListByCondition(T t) {
        return mapper.select(t);
    }

    @Override
    public PageInfo<T> queryPageListByCondition(Integer starPage, Integer pageSize, T t) {
        if (starPage<0||pageSize<0){
            return  null;
        }
        PageHelper.startPage(starPage,pageSize);
        List<T> list=mapper.select(t);
        return  new PageInfo<T>(list);
    }

    @Override
    public void save(T t) {
      mapper.insertSelective(t);
    }
/*updateByPrimaryKeySelective
updateByPrimaryKey
前者只是更新新的model中不为空的字段。
后者则会将为空的字段在数据库中置为NULL。*/
    @Override
    public void update(T t) {
   mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void delete(Long id) {
  mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByConditions(Class<T> clazz, String property, List<Object> values) {
        Example example=new Example(clazz);
        example.createCriteria().andIn(property,values);
        mapper.deleteByExample(example);

    }
}
