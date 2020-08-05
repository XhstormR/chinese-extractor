package com.xhstormr.web.app.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author zhangzf
 * @create 2018/6/19 16:46
 */
@Api(tags = ["主页"])
@Controller
class HomeController {

    @ApiOperation("Swagger UI", notes = "这是第一个 API")
    @GetMapping("/")
    fun home() = "redirect:/swagger-ui/"
}
