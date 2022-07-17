package cn.spring.learning.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 一般Api返回结果结构
 * @author ZX
 * @date 2020/7/24 11:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("API返回结果")
public class ApiResult<T> {

    /**
     * 错误码
     */
    @ApiModelProperty("错误码：0-正常，其他异常")
    private Integer code;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String msg;

    /**
     * 返回的数据
     */
    @ApiModelProperty("返回的数据")
    private T data;

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(0, "success", data);
    }
}
