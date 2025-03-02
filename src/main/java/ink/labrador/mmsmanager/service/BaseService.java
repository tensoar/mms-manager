package ink.labrador.mmsmanager.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface BaseService<E, M extends BaseMapper<E>> extends IService<E> {
    M getBaseMapper();
    E getOne(E e);
    E getOne(E e, boolean throwEx);
    Map<String, Object> getMap(E e);
    <V> V getObj(E e, Function<? super Object, V> mapper);
    long count(E e);
    List<Object> listObjs(E e);
    <V> List<V> listObjs(E e, Function<? super Object, V> mapper);
    <P extends IPage<E>> P page(P page, E e);
    List<Map<String, Object>> listMaps(E e);
    List<E> list(E e);
//    boolean saveOrUpdate(E entity, E values);
    boolean remove(E e);
    boolean update(E e);
    boolean update(E e, E toUpdate);
}