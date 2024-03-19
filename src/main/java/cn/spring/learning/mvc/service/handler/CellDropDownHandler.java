package cn.spring.learning.mvc.service.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * 特定设备直采通道，【所属厂站名称】增加下拉选项
 *
 * @author Jinhua-Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CellDropDownHandler implements SheetWriteHandler {

    /**
     * 列号 -> 下拉内容
     */
    private Map<Integer, List<String>> dropDownOptional;

    private boolean withHead;

    /**
     * 记录行数
     */
    private Integer rowCount;

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional) {
        this(dropDownOptional, true, null);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional, boolean withHead) {
        this(dropDownOptional, withHead, null);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional, Integer rowCount) {
        this(dropDownOptional, true, rowCount);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional, boolean withHead, Integer rowCount) {
        this.dropDownOptional = dropDownOptional;
        this.withHead = withHead;
        this.rowCount = rowCount;
        if (ObjectUtils.isEmpty(rowCount)) {
            this.rowCount = 10_000;
        }
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        // 为空，不需要增加下拉选项
        if (ObjectUtils.isEmpty(dropDownOptional)) {
            return;
        }
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();

        dropDownOptional.forEach((celIndex, strings) -> {
            // 区间设置
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(
                    withHead ? 2 : 1, rowCount, celIndex, celIndex
            );
            // 下拉内容
            DataValidationConstraint constraint = validationHelper
                    .createExplicitListConstraint(strings.toArray(new String[0]));
            DataValidation dataValidation = validationHelper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(dataValidation);
        });

    }
}
