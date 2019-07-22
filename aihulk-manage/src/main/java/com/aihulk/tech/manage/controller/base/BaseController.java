package com.aihulk.tech.manage.controller.base;

import com.aihulk.tech.entity.entity.BaseEntity;
import com.aihulk.tech.manage.service.BaseService;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.aihulk.tech.manage.vo.base.ResponsePageVo;
import com.aihulk.tech.manage.vo.base.ResponseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhangyibo
 * @title: BaseController
 * @projectName aihulk
 * @description: BaseController
 * @date 2019-07-0316:04
 */
public abstract class BaseController<T extends BaseEntity, R, S extends BaseService<T, ?>> {

    @Autowired
    protected S baseService;

    @GetMapping(value = "")
    public BaseResponseVo<List<R>> select(@RequestParam(required = false) Integer start,
                                          @RequestParam(required = false) Integer pageSize,
                                          T t) {
        if (start != null && start > 0 && pageSize != null && pageSize > 0) {
            IPage<T> iPage = baseService.selectPage(t, new Page<>(start, pageSize));
            List<T> records = iPage.getRecords();
            List<R> results = this.map(records);
            return new ResponsePageVo<R>().buildSuccess(results, "ok", iPage.getCurrent(), iPage.getSize());
        } else {
            List<T> list = baseService.select(t);
            List<R> results = this.map(list);
            return new ResponseVo<List<R>>().buildSuccess(results, "ok");
        }
    }

    /**
     * 将数据库查询的实体转换成要展示的Vo实体
     *
     * @param tList
     * @return
     */
    protected abstract List<R> map(List<T> tList);

    @PostMapping(value = "")
    public ResponseVo<Void> add(@RequestBody @NonNull T t) {
        baseService.add(t);
        return new ResponseVo<Void>().buildSuccess("添加成功");
    }

    @PutMapping(value = "")
    public ResponseVo<Void> update(@RequestBody @NonNull T t) {
        checkNotNull(t.getId(), "id不能为空");
        checkArgument(t.getId() > 0, "id参数不合法");
        baseService.update(t);
        return new ResponseVo<Void>().buildSuccess("修改成功");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseVo<Void> delete(@PathVariable(value = "id") Integer id) {
        checkNotNull(id, "id不能为空");
        checkArgument(id > 0, "id参数不合法");
        baseService.delete(id);
        return new ResponseVo<Void>().buildSuccess("删除成功");
    }

}
