package cn.spring.learning.mvc.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * 静态
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2022/12/16 19:58
 */
@Controller
@RequestMapping(value = "/model-view")
public class MvcModelViewController {

    @GetMapping(value = "main")
    public String toMainPage() {
        // 路径开头要带上斜杠，否则无法匹配到
        // 默认已经有prefix = /static/
        // 转发，路径不会变
        return UrlBasedViewResolver.FORWARD_URL_PREFIX + "/templates/main.html";
    }

    @GetMapping(value = "main2")
    public String toMainPage2() {
        // 路径开头要带上斜杠，否则无法匹配到
        // 默认已经有prefix = /resources/
        // 重定向，是另一个请求了，信息会变
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/main2.html";
    }
}
