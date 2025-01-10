package com.example.export.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName 参数包含多个 name（excel名） 多个sheet数据
 * @description 传入一个多个List<T> 将多个List写入一个Excle的多个Sheet
 * @date 2025/1/9 20:10
 **/


//输入输出流
//1、读取一个文件 获取一个文件的输入流
//2、写一个文件 获取一个文件的输出流
//3、将一个A文件内容写进B文件 获取A的输入流 获取B的输出流 将输入流传到输出流 完成了文件A写到文件B的过程







public class ExportMutiSheetToOneExcelUtils {
    /**
     * 获取Date的月份
     */
    static int getMonth(Date Time) {

        LocalDate localDate = Time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;

    }
    /*
    *  获取日期
    **/
    private static String formatToDate(Date receiveCliDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = dateFormat.format(receiveCliDate);
        return formattedDate;
    }





/**
* @author Mr.Lan
* @Description 获取任意个List的类型
* @Date  2025/1/9 21:15
* @Param
* @return
**/
    public static List<Class> processLists(List<?>... lists) {
        List<Class> classes = new ArrayList<>();
        for (List<?> list : lists) {
            // 使用反射来获取 List 中元素的实际类型
            if (!list.isEmpty()) {
                Class<?> itemType = list.get(0).getClass();
                classes.add(itemType);
            } else {
                classes.add(null);
            }
        }
        return classes;
    }


    //删除文件
    static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String exportMutiDtaToMutiSheetToOneExcel( HttpServletResponse response,List<?>... lists) throws Exception {
        //获取类型
        List<Class> classes = processLists(lists);
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        // 设置Excel文件名
        String excelFileName = System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + excelFileName + "\"");
        //需要合并的列（不超过30列且均合并）
        int[] mergeColumeIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
        //开始合并的行（除去表头）2表示表头有两行
        int mergeRowIndex=2;
        //获取动态字段
        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String titleDate = formatToDate(date);
        String month = String.valueOf(getMonth(date));
        //合并策略
        ExcelMergeUtil excelFillCellMergeStrategy = new ExcelMergeUtil(mergeRowIndex, mergeColumeIndex, titleDate, month);
        //excel样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置标题头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置内容水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置内容垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //写进excel文件
        try (ExcelWriter excelWriter = EasyExcel.write(excelFileName).build()) {
            int index = 0;
            for (List<?> list : lists) {
                Class aClass = classes.get(index);
                if (aClass == null)
                    continue;
                index++;
                //excel文件名从1开始到size
                String sheetName = "sheet" + index;
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName)
                        .registerWriteHandler(excelFillCellMergeStrategy)
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .head(aClass).build();
                excelWriter.write(list, writeSheet);
            }
        }
        //将excel输入流写到response输出流中
//        InputStream inputStream = new FileInputStream(excelFileName);
//        IOUtils.copy(inputStream, response.getOutputStream());
        //删除excel文件
//        deleteFile(excelFileName);
        //不删除返回可再次合并到一个zip 且不要写输出流 等到zip再写
        return excelFileName;
    }

}
