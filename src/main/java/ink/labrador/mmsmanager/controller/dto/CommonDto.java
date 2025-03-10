package ink.labrador.mmsmanager.controller.dto;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongTransformer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public class CommonDto {
    @Data
    @Schema(description = "分页信息")
    public static class PageDto {
        @Schema(description = "当前页码")
        @NotNull(message = "页码不能为空")
        @Min(value = 1, message = "页码不能小于1")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        public Long current;

        @Schema(description = "分页大小")
        @NotNull(message = "分页大小不能为空")
        @Min(value = 1, message = "分页大小不能小于1")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        public Long size;

        public <T> Page<T> mapPage() {
            return new Page<T>(current, size);
        }

        public <T> Page<T> mapPage(OrderItem order) {
            Page<T> page = new Page<T>(current, size);
            page.addOrder(order);
            return page;
        }

        public <T> Page<T> mapPage(List<OrderItem> orders) {
            Page<T> page = new Page<T>(current, size);
            page.addOrder(orders);
            return page;
        }
    }
}
