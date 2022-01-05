package cn.demo.springlearning.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2021/12/31 16:11
 */
@RestController(value = "/learning/file")
public class MyFileReceiveController {

    @PostMapping(value = "/rec")
    public void fileReceive(@RequestParam("file") MultipartFile file) {
        // MultipartFile就和java.io.File一样，它只是文件的句柄信息，通过它可以操作对应文件，它本身不是文件，
        // 待到执行诸如read()方法的时候，它才会执行读取内容的操作。

        // 在代码执行到该Controller的时候，它的内容可以是【缓存在内存或是磁盘中】，当用户操作完成后，缓存的内容将会被清空。
        // 具体过程是什么样的呢？IO理解，SpringMVC及Servlet过程阅读。
    }
}
