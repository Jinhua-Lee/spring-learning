package cn.spring.learning.mvc.service.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 特定设备直采通道，【所属厂站名称】增加下拉选项
 *
 * @author Jinhua-Lee
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings(value = "unused")
public class CellDropDownHandler implements SheetWriteHandler {

    /**
     * 列号 -> 下拉内容
     */
    private Map<Integer, List<String>> dropDownOptional;

    /**
     * 列号，下拉引用位置
     */
    private Map<Integer, String> dropDownFormulaReference;

    /**
     * 写出时是否带一行表头
     */
    private boolean withHead;

    /**
     * 记录行数
     */
    private Integer rowCount;

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional,
                               Map<Integer, String> dropDownFormulaReference) {
        this(dropDownOptional, dropDownFormulaReference, true, null);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional,
                               Map<Integer, String> dropDownFormulaReference, boolean withHead) {
        this(dropDownOptional, dropDownFormulaReference, withHead, null);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional,
                               Map<Integer, String> dropDownFormulaReference, Integer rowCount) {
        this(dropDownOptional, dropDownFormulaReference, true, rowCount);
    }

    public CellDropDownHandler(Map<Integer, List<String>> dropDownOptional,
                               Map<Integer, String> dropDownFormulaReference, boolean withHead, Integer rowCount) {
        this.dropDownOptional = dropDownOptional;
        this.dropDownFormulaReference = dropDownFormulaReference;
        this.withHead = withHead;
        this.rowCount = rowCount;
        if (ObjectUtils.isEmpty(rowCount)) {
            this.rowCount = 10_000;
        }
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();

        // 处理值列表的枚举下拉
        handleDropDownOptions(sheet, validationHelper);

        // 处理引用的枚举下拉
        List<String> dictSheetNames = handleDropDownFormulaReference(sheet, validationHelper);

        // 隐藏字典sheet页
        dictSheetNames.forEach(dictSheetName -> {
            int sheetNum = writeWorkbookHolder.getWorkbook().getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Sheet sheetAt = workbook.getSheetAt(i);
                if (Objects.equals(sheetAt.getSheetName(), dictSheetName)) {
                    workbook.setSheetHidden(i, true);
                    return;
                }
            }
        });
    }

    private void handleDropDownOptions(Sheet sheet, DataValidationHelper validationHelper) {
        Optional.ofNullable(dropDownOptional).orElse(Collections.emptyMap()).forEach((celIndex, strings) -> {
            // 区间设置
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(
                    withHead ? 1 : 0, rowCount, celIndex, celIndex
            );
            // 下拉内容
            DataValidationConstraint constraint = validationHelper
                    .createExplicitListConstraint(strings.toArray(new String[0]));
            DataValidation dataValidation = validationHelper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(dataValidation);
        });
    }

    private List<String> handleDropDownFormulaReference(Sheet sheet, DataValidationHelper validationHelper) {
        List<String> dictSheetNames = new ArrayList<>();
        Optional.ofNullable(dropDownFormulaReference).orElse(Collections.emptyMap()).forEach((celIndex, formula) -> {
            dictSheetNames.add(resolveSheetName(formula));

            // 区间设置
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(
                    withHead ? 1 : 0, rowCount, celIndex, celIndex
            );
            // 下拉内容
            DataValidationConstraint constraint = validationHelper
                    .createFormulaListConstraint(formula);
            DataValidation dataValidation = validationHelper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(dataValidation);
        });
        return dictSheetNames;
    }

    private String resolveSheetName(String formula) {
        final String sheetNamePrefix = "=";
        final String sheetNameSuffix = "!";
        if (ObjectUtils.isEmpty(formula)
                || !formula.startsWith(sheetNamePrefix)
                || !formula.contains(sheetNameSuffix)) {
            throw new IllegalArgumentException(
                    String.format("unsupported formula format = %s , please check.", formula)
            );
        }
        int preIndex = formula.indexOf(sheetNamePrefix);
        int sufIndex = formula.indexOf(sheetNameSuffix);
        if (preIndex >= sufIndex) {
            throw new IllegalArgumentException(
                    String.format("unsupported formula format = %s , please check.", formula)
            );
        }
        return formula.substring(preIndex + 1, sufIndex);
    }
}
