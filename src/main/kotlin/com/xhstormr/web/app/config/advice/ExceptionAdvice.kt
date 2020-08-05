package com.xhstormr.web.app.config.advice

import com.xhstormr.web.domain.model.RestResponse
import com.xhstormr.web.domain.util.getLogger
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
import javax.servlet.http.HttpServletRequest

/**
 * @author zhangzf
 * @create 2018/7/31 12:05
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
class ExceptionAdvice {

    private companion object {
        const val DEFAULT_MESSAGE = "请求错误"

        val LOGGER = getLogger()

        fun resolve(e: Exception, req: HttpServletRequest): RestResponse<String> {
            LOGGER.error(
                "---Exception Handler--- HOST: {} USER: {} URL: {},{} ERROR: {}",
                req.remoteHost,
                req.remoteUser,
                req.method,
                req.requestURI,
                e.message
            )
            e.printStackTrace()
            return RestResponse.fail(DEFAULT_MESSAGE)
        }

        fun resolve(bindingResult: BindingResult) = RestResponse.fail<String>(
            bindingResult.fieldError?.defaultMessage
                ?: bindingResult.globalError?.defaultMessage
                ?: DEFAULT_MESSAGE
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException) =
        resolve(e.bindingResult)

    @ExceptionHandler(BindException::class)
    fun handleException(e: BindException) =
        resolve(e.bindingResult)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, req: HttpServletRequest) =
        resolve(e, req)

    @ExceptionHandler(
        AccessDeniedException::class,
        IllegalStateException::class,
        IllegalArgumentException::class,
        HttpRequestMethodNotSupportedException::class
    )
    fun handleException(e: Exception) =
        RestResponse.fail<String>(e.message ?: DEFAULT_MESSAGE)

    @ExceptionHandler(
        NoSuchElementException::class
    )
    fun handleNoSuchElementException() =
        RestResponse.fail<String>("资源不存在")

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException() =
        RestResponse.fail<String>("文件超出最大上传大小")
}
