package ink.labrador.mmsmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.labrador.mmsmanager.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseServiceImpl <E, M extends BaseMapper<E>> extends ServiceImpl<M, E> implements BaseService<E, M> {
    public E getOne(E e) {
        return getOne(new QueryWrapper<>(e));
    }
    public E getOne(E e, boolean throwEx) {
        return getOne(new QueryWrapper<>(e), throwEx);
    }
    public Map<String, Object> getMap(E e) {
        return getMap(new QueryWrapper<>(e));
    }
    public <V> V getObj(E e, Function<? super Object, V> mapper) {
        return getObj(new QueryWrapper<>(e), mapper);
    }
    public long count(E e) {
        return count(new QueryWrapper<>(e));
    }
    public List<Object> listObjs(E e) {
        return listObjs(new QueryWrapper<>(e));
    }
    public <V> List<V> listObjs(E e, Function<? super Object, V> mapper) {
        return listObjs(new QueryWrapper<>(e), mapper);
    }
    public <P extends IPage<E>> P page(P page, E e) {
        return page(page, new QueryWrapper<>(e));
    }
    public List<Map<String, Object>> listMaps(E e) {
        return listMaps(new QueryWrapper<>(e));
    }
    public List<E> list(E e) {
        return list(new QueryWrapper<>(e));
    }
//    public boolean saveOrUpdate(E entity) {
//        return super.saveOrUpdate(entity);
//    }
    public boolean remove(E e) {
        return remove(new QueryWrapper<>(e));
    }
    public boolean update(E e) {
        return update(new QueryWrapper<>(e));
    }
    public boolean update(E e, E toUpdate) {
        return update(e, new QueryWrapper<>(toUpdate));
    }

}

