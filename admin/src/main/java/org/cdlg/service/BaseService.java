package org.cdlg.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Auther: wqs
 * @Date: 2018/8/12 0012 20:41
 * @Description: I LOVE IT？
 */
public interface BaseService<T> {
    public T queryById(Long id);


    public List<T> queryAll();

    public T queryOneByCondition(T t);

    public List<T> queryListByCondition(T t);

    public PageInfo<T> queryPageListByCondition(Integer starIndex, Integer pageSize, T t);

    public void save(T t);

    public void update(T  t);

    public void delete(Long id);

    public  void deleteByConditions(Class<T> clazz,String property,List<Object> values);


}
