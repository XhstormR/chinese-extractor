package com.xhstormr.web.app.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * @author zhangzf
 * @create 2018/6/19 16:46
 */
@Tag(name = "主页")
@Controller
class HomeController {

    @Operation(summary = "Swagger UI", description = "这是第一个 API")
    @GetMapping("/")
    fun home() = "redirect:/swagger-ui/"
}
