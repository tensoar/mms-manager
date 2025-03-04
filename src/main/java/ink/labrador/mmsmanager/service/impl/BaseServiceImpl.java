package ink.labrador.mmsmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.labrador.mmsmanager.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseServiceImpl <E, M extends BaseMapper<E>> extends ServiceImpl<M, E> implements BaseService<E, M> {
    public E getOne(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return getOne(cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public E getOne(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, boolean throwEx) {
        return getOne(cb.apply(Wrappers.<E>lambdaQuery()), throwEx);
    }
    public Map<String, Object> getMap(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return getMap(cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public <V> V getObj(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, Function<? super Object, V> mapper) {
        return getObj(cb.apply(Wrappers.<E>lambdaQuery()), mapper);
    }
    public long count(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return count(cb.apply(Wrappers.<E>lambdaQuery()));
    }
//    public List<Object> listObjs(E e) {
//        return listObjs(new QueryWrapper<>(e));
//    }
    public <V> List<V> listObjs(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb, Function<? super Object, V> mapper) {
        return listObjs(cb.apply(Wrappers.<E>lambdaQuery()), mapper);
    }
    public <P extends IPage<E>> P page(P page, Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return page(page, cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public List<Map<String, Object>> listMaps(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return listMaps(cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public List<E> list(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return list(cb.apply(Wrappers.<E>lambdaQuery()));
    }
//    public boolean saveOrUpdate(E entity) {
//        return super.saveOrUpdate(entity);
//    }
    public boolean remove(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return remove(cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public boolean update(Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return update(cb.apply(Wrappers.<E>lambdaQuery()));
    }
    public boolean update(E e, Function<LambdaQueryWrapper<E>, LambdaQueryWrapper<E>> cb) {
        return update(e, cb.apply(Wrappers.<E>lambdaQuery()));
    }

    public LambdaQueryWrapper<E> newWrapper() {
        return Wrappers.<E>lambdaQuery();
    }

}

