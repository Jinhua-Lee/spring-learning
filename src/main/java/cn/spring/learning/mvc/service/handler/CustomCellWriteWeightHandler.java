package cn.spring.learning.mvc.service.handler;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Jinhua-Lee
 */
@Component
public class CustomCellWriteWeightHandler extends AbstractColumnWidthStyleStrategy {

    private static final int MAX_COLUMN_WIDTH = 200;
    private static final int DEFAULT_MAP_SIZE = 8;

    private final Map<Integer, Map<Integer, Integer>> sheet2Index2Length = new HashMap<>(DEFAULT_MAP_SIZE);

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList,
                                  Cell cell, Head head, Integer integer, Boolean isHead) {
        // 如果是表头，或者数据不为空，则需要设置宽度
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            // 列 -> 宽度
            Map<Integer, Integer> maxColumnWidthMap = sheet2Index2Length.computeIfAbsent(
                    writeSheetHolder.getSheetNo(),
                    k -> new HashMap<>(DEFAULT_MAP_SIZE)
            );

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > MAX_COLUMN_WIDTH) {
                    columnWidth = MAX_COLUMN_WIDTH;
                }

                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    Sheet sheet = writeSheetHolder.getSheet();
                    // 为sheet页的某个列设置列宽
                    sheet.setColumnWidth(cell.getColumnIndex(), columnWidth * 200);
                }
            }
        }
    }

    /**
     * 计算长度
     *
     * @param cellDataList 单元格数据列表
     * @param cell         单元格
     * @param isHead       是否表头
     * @return 计算数据长度
     */
    @SuppressWarnings(value = "rawtypes")
    private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch (type) {
                    case STRING:
                        // 换行符（数据需要提前解析好）
                        int index = cellData.getStringValue().indexOf("\n");
                        if (index == -1) {
                            return cellData.getStringValue().getBytes().length + 1;
                        } else {
                            return Arrays.stream(cellData.getStringValue().split("\n"))
                                    .max(Comparator.comparing(String::length))
                                    .map(String::getBytes).map(bytes -> bytes.length + 1).orElse(0);
                        }
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}
