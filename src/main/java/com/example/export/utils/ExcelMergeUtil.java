package com.example.export.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.PropertyPlaceholderHelper;
import java.util.List;
import java.util.Properties;

/**
 * author:lanjie
 * 单元格合并策略
 * describe：合并相同字段的列，但需要判断首列是否合并（条件合并）+动态表头
 * ps:可复用
 */
@Slf4j
public class ExcelMergeUtil implements CellWriteHandler {
    //需要合并的列数组
    private int[] mergeColumnIndex;
    //合并起始行
    private int mergeRowIndex;


    //新增动态表头数据
    private String titleDate;
    private String titleMonth;
    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");

    public ExcelMergeUtil(int mergeRowIndex, int[] mergeColumnIndex, String titleDate, String titleMonth) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
        this.titleDate = titleDate;
        this.titleMonth = titleMonth;
    }

    public ExcelMergeUtil(int mergeRowIndex, int[] mergeColumnIndex) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
    }


    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {
        List<String> headNameList = head.getHeadNameList();
        if (CollectionUtils.isNotEmpty(headNameList)) {
            Properties properties = new Properties();
            //将动态表头数据赋值给对应的变量(相当于占位符)在注解中使用可以直接获取到相应的值
            properties.setProperty("titleDate", titleDate);
            properties.setProperty("titleMonth", titleMonth);
            //在第一行表头生效这些占位符
//                headNameList.set(0, placeholderHelper.replacePlaceholders(headNameList.get(0), properties));
            //在每行的表头中都生效
            for (int i = 0; i < headNameList.size(); i++) {
                headNameList.set(i, placeholderHelper.replacePlaceholders(headNameList.get(i), properties));
            }
        }
    }


    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();

        if (curRowIndex > mergeRowIndex) {
            for (int i = 0; i < mergeColumnIndex.length; i++) {
                if (curColIndex == mergeColumnIndex[i]) {
                    mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                    break;
                }
            }
        }
    }


    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        Object curData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        Object preData = preCell.getCellTypeEnum() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
        // 将当前单元格数据与上一个单元格数据比较
        Boolean dataBool = preData.equals(curData);
        //判断上一列是否合并 如果没合并则返回false 否则返回true（前提是当前列不是第一列）
        // 判断当前列上一列的当前行与上一行是否相等
        Boolean bool = extracted(cell, curRowIndex, curColIndex);




//      --------------------------------------------bool再增合并逻辑 如果当前列满足合并 需判断上一列是否合并 不加bool则直接合并----------------------------------------------------------
//1、相等需要判断上一列是否合并 没合并则当前行也不能合并
        if (dataBool && bool) {
//2、只要相等就合并
//        if (dataBool) {
                Sheet sheet = writeSheetHolder.getSheet();
                List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
                boolean isMerged = false;
                for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                    CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                    // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                    if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                        sheet.removeMergedRegion(i);
                        cellRangeAddr.setLastRow(curRowIndex);
                        sheet.addMergedRegion(cellRangeAddr);
                        isMerged = true;
                    }
                }
                // 若上一个单元格未被合并，则新增合并单元
                if (!isMerged) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                    sheet.addMergedRegion(cellRangeAddress);
                }
            }
    }

    private Boolean extracted(Cell cell, int curRowIndex, int curColIndex) {
        // 判断上一列的当前行与上一行是否相等
        Boolean bool = false;
        if (curColIndex > 0) {  // 确保不是第一列
            Cell curPrevColCell = cell.getSheet().getRow(curRowIndex).getCell(curColIndex - 1);
            Cell prePrevColCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex - 1);

            Object curPrevColValue = getCellValue(curPrevColCell);
            Object prePrevColValue = getCellValue(prePrevColCell);

            bool = curPrevColValue.equals(prePrevColValue);
        }
        else{
            bool=true;
        }
        return bool;
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue(); // 返回字符串类型的值
            case NUMERIC:
                return cell.getNumericCellValue(); // 返回数字类型的值
            case BOOLEAN:
                return cell.getBooleanCellValue(); // 返回布尔值类型的值
            case FORMULA:
                return cell.getCellFormula(); // 如果是公式类型，返回公式表达式
            case ERROR:
                return cell.getErrorCellValue(); // 返回错误值
            default:
                return null;
        }
    }





}
