package com.xhstormr.web.app.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * @author zhangzf
 * @create 2018/12/27 22:15
 */
@Hidden
@RestController
class ErrorController(errorAttributes: ErrorAttributes) : AbstractErrorController(errorAttributes) {

    companion object {
        private const val ERROR_PATH = "/error"
    }

    override fun getErrorPath() = ERROR_PATH

    @RequestMapping(ERROR_PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
    fun error(req: HttpServletRequest): Nothing =
        error(super.getErrorAttributes(req, ErrorAttributeOptions.defaults())["error"].toString())
}
