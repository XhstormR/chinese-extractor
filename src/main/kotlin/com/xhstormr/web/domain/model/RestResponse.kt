package com.xhstormr.web.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.Instant

@ApiModel("请求响应体")
data class RestResponse<T>(
    @field:ApiModelProperty("响应数据")
    var payload: T? = null,
    @field:ApiModelProperty("请求成功")
    var success: Boolean = false,
    @field:ApiModelProperty("错误信息")
    var msg: String? = null,
    @JsonIgnore
    @field:ApiModelProperty("状态码")
    var code: Int = 0,
    @JsonIgnore
    @field:ApiModelProperty("响应时间")
    val timestamp: Long = Instant.now().epochSecond
) {
    fun peek(runnable: Runnable): RestResponse<T> {
        runnable.run()
        return this
    }

    fun success(success: Boolean): RestResponse<T> {
        this.success = success
        return this
    }

    fun payload(payload: T): RestResponse<T> {
        this.payload = payload
        return this
    }

    fun code(code: Int): RestResponse<T> {
        this.code = code
        return this
    }

    fun message(msg: String?): RestResponse<T> {
        this.msg = msg
        return this
    }

    companion object {
        fun <T> ok() = RestResponse<T>().success(true)

        fun <T> ok(payload: T) = RestResponse<T>().success(true).payload(payload)

        fun <T> ok(payload: T, code: Int) = RestResponse<T>().success(true).payload(payload).code(code)

        fun <T> fail() = RestResponse<T>().success(false)

        fun <T> fail(message: String?) = RestResponse<T>().success(false).message(message)

        fun <T> fail(message: String?, code: Int) = RestResponse<T>().success(false).message(message).code(code)
    }
}
