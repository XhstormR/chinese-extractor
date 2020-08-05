package com.xhstormr.web.app.config.handler

import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

class RequestLogger : AbstractRequestLoggingFilter() {

    init {
        super.setIncludeClientInfo(true)
        super.setBeforeMessagePrefix("[")
    }

    override fun shouldLog(request: HttpServletRequest) =
        logger.isInfoEnabled

    override fun beforeRequest(request: HttpServletRequest, message: String) =
        logger.info(message)

    override fun afterRequest(request: HttpServletRequest, message: String) = Unit
}
