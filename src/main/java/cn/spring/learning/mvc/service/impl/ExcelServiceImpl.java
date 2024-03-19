package cn.spring.learning.mvc.service.impl;

import cn.spring.learning.mvc.dto.DropDownDemoDTO;
import cn.spring.learning.mvc.service.ExcelService;
import cn.spring.learning.mvc.service.handler.CellDropDownHandler;
import cn.spring.learning.mvc.types.GenderType;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Jinhua-Lee
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void dropDown(ServletOutputStream out) {
        ExcelWriterBuilder writerBuilder = EasyExcel.write(out)
                .excelType(ExcelTypeEnum.XLSX);
        ExcelWriterSheetBuilder dropDownSheetBuilder = writerBuilder
                .sheet("drop_down")
                .registerWriteHandler(new CellDropDownHandler(
                        Collections.singletonMap(2,
                                Arrays.stream(GenderType.values()).map(GenderType::getText)
                                        .collect(Collectors.toList())
                        ),
                        null
                ))
                .head(DropDownDemoDTO.class)
                .needHead(true);
        writerBuilder.build()
                .write(Collections.emptyList(), dropDownSheetBuilder.build())
                .finish();
    }
}
