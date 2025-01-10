package com.example.export.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName 参数包含多个 name（excel名） 多个sheet数据
 * @description 传入多个excel文件 返回一个zip 并写入response
 * @date 2025/1/9 20:10
 **/


//输入输出流
//1、读取一个文件 获取一个文件的输入流
//2、写一个文件 获取一个文件的输出流
//3、将一个A文件内容写进B文件 获取A的输入流 获取B的输出流 将输入流传到输出流 完成了文件A写到文件B的过程







public class ExportMutiExcelToOneZipUtils {
    /**
     * @param file 文件
     * @param zos  输出流
     * @description 将文件的数据写到输出流中
     */
    public static void addFileToZip(File file, ZipOutputStream zos) throws IOException {
        //1、定义输入流读取文件数据
        InputStream fileInputStream = new FileInputStream(file);
        //2、获取excel文件的文件名并转成ZipEntry格式（ZipEntry 代表的是 ZIP 文件中的一个条目（即文件或目录））:方便多次存储输出流
        ZipEntry zipEntry = new ZipEntry(file.getName());
        //3、存入条目的文件名
        zos.putNextEntry(zipEntry);
        //4、将数据流读入上述文件条目中
        byte[] bytes = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bytes)) != -1) {
            //读的内容会自动放到zip条目中，因此zipentry再输出流读完需要关闭
            zos.write(bytes, 0, len);
        }
        //关闭文件流
        zos.closeEntry();
        fileInputStream.close();
    }


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



    //删除文件
    static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * @return
     * @author Mr.Lan
     * @Description 获取任意个List的类型
     * @Date 2025/1/9 21:15
     * @Param
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


    public static void exportMutiExcelToZip(HttpServletResponse response, String... fileNames) throws Exception {
        //设置返回格式
        response.setContentType("application/zip");
        response.setCharacterEncoding("UTF-8");
        //设置zip文件名 这里的文件名代替相对路径
        String zipFileName = System.currentTimeMillis() + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
        //需要合并的列（不超过30列且均合并）
        int[] mergeColumeIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
        //开始合并的行（除去表头）2表示表头有两行
        int mergeRowIndex=2;
        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String titleDate = formatToDate(date);
        String month = String.valueOf(getMonth(date));
        ExcelMergeUtil excelFillCellMergeStrategy = new ExcelMergeUtil(2, mergeColumeIndex, titleDate, month);
        //设置样式 标题和内容
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置标题头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置内容水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置内容垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 创建输出流：写文件
        FileOutputStream fos = new FileOutputStream(zipFileName);
        //将文件输出流转成zip文件的输出流
        ZipOutputStream zos = new ZipOutputStream(fos);

        //将多个excel文件写到zip
        for (String fileName : fileNames) {
            // 压缩文件
            File file1 = new File(fileName);
            //将excel写到zip中
            addFileToZip(file1, zos);
            //删除excel
            deleteFile(fileName);
        }
        //关闭输出流
        zos.close();
        fos.close();
        //将压缩文件输入流传给response输出流
        InputStream fileInputStream = new FileInputStream(zipFileName);
        //将zip文件的输入流写到response的输出流中 前端获取输出流并指定一个路径就可以获取到zip文件
        OutputStream outputStream = response.getOutputStream();
        //实现输出流到输入流
        byte[] bytes = new byte[1024 * 8];
        int len;
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        fileInputStream.close();
        outputStream.close();
//      删除zip文件
//        deleteFile(zipFileName);
    }

}

