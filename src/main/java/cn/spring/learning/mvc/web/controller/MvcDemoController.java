package cn.spring.learning.mvc.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 10:19
 */
@RestController
public class MvcDemoController {

    @PutMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/test-filter-order")
    public boolean testFilterOrder() {
        return false;
    }
}
