package cn.spring.learning.mvc.service;

import javax.servlet.ServletOutputStream;

/**
 * @author Jinhua-Lee
 */
public interface ExcelService {

    /**
     * 下拉列表测试
     *
     * @param out servlet文件输出流
     */
    void dropDown(ServletOutputStream out);
}
