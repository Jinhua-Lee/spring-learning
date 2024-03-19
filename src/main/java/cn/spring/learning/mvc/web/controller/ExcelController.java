package cn.spring.learning.mvc.web.controller;

import cn.spring.learning.mvc.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Jinhua-Lee
 */
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {

    private ExcelService excelService;

    @GetMapping(value = "/drop-down")
    public void myDropDown(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

        try {
            String fileName = URLEncoder.encode("下拉列表测试.xlsx",
                    StandardCharsets.UTF_8.toString().replace("\\+", "%20")
            );
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        } catch (IOException e) {
            response.setStatus(500);
            throw new IllegalStateException(
                    String.format("resolve file name error: ex = %s",
                            e.getMessage()
                    )
            );
        }
        try {
            this.excelService.dropDown(response.getOutputStream());
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("resolve file error: ex = %s",
                            e.getMessage()
                    )
            );
        }
    }

    @Autowired
    public void setExcelService(ExcelService excelService) {
        this.excelService = excelService;
    }
}
