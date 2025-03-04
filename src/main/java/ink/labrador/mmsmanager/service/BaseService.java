package ink.labrador.mmsmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface BaseService<E, M extends BaseMapper<E>> extends IService<E> {
    M getBaseMapper();
    E getOne(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    E getOne(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, boolean throwEx);
    Map<String, Object> getMap(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    <V> V getObj(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, Function<? super Object, V> mapper);
    long count(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
//    List<Object> listObjs(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    <V> List<V> listObjs(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, Function<? super Object, V> mapper);
    <P extends IPage<E>> P page(P page, Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    List<Map<String, Object>> listMaps(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    List<E> list(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
//    boolean saveOrUpdate(E entity, E values);
    boolean remove(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    boolean update(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb);
    boolean update(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, E toUpdate);
    LambdaQueryWrapper<E> newWrapper();
}