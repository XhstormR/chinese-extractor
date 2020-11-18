package com.xhstormr.web.domain.model

import com.xhstormr.web.app.common.Const
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Min

/**
 * @author zhangzf
 * @create 2018/8/1 9:45
 */
@Schema(name = "分页请求")
data class PageRequest(
    @field:Min(message = "非法页码", value = 0)
    @field:Schema(description = "页码", example = 0.toString())
    val page: Int = 0,
    @field:Range(message = "非法分页大小", min = 1, max = 100)
    @field:Schema(description = "分页大小", example = 5.toString())
    val size: Int = 5,
    @field:Schema(description = "排序参数", example = Const.DEFAULT_SORT)
    val sort: String? = null
) {
    @Hidden
    val offset = page * size
}
