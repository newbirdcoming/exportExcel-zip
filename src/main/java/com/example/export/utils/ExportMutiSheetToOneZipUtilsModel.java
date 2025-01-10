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
 * @ClassName 参数包括多个List数据
 * @description 传入一个多个List<T> 将每个List均写入一个excel 然后合并成zip
 * @date 2025/1/9 20:10
 **/


//输入输出流
//1、读取一个文件 获取一个文件的输入流
//2、写一个文件 获取一个文件的输出流
//3、将一个A文件内容写进B文件 获取A的输入流 获取B的输出流 将输入流传到输出流 完成了文件A写到文件B的过程







public class ExportMutiSheetToOneZipUtilsModel {
    /**
     * @description 将文件的数据写到输出流中
     * @param file 文件
     * @param zos 输出流
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

    //删除文件
    static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
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


    public static String exportMutiDataToMutiExcelToOneZip( HttpServletResponse response,List<?>... lists) throws Exception {
        //获取类型
        List<Class> classes=processLists(lists);
        //设置
        response.setContentType("application/zip");
        response.setCharacterEncoding("UTF-8");
        //设置zip文件名 这里的文件名代替相对路径
        String zipFileName =  System.currentTimeMillis() + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");

        //合并策略：
//        int[] mergeColumeIndex1 ={1,2,3,4,5,6,7,8,9,10,11,12,20,21};
//        int[] mergeColumeIndex2 ={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
//        int[] mergeColumeIndex3 ={1,2,3,4,5,6,7,8,9,10,11,12};
//        int[] mergeColumeIndex4 ={1,2,3,4,5,6,7,8,9,10,11,12,13};

        int[] mergeColumeIndex ={0,1,2,3,4,5,6,7};
        LocalDate now = LocalDate.now();
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String titleDate=formatToDate(date);
        String month=String.valueOf(getMonth(date));
        ExcelMergeUtil excelFillCellMergeStrategy = new ExcelMergeUtil(2,mergeColumeIndex,titleDate,month);
//        ExcelMergeUtil excelFillCellMergeStrategy2 = new ExcelMergeUtil(2,mergeColumeIndex2,titleDate,month);
//        ExcelMergeUtil excelFillCellMergeStrategy3 = new ExcelMergeUtil(2,mergeColumeIndex3,titleDate,month);
//        ExcelMergeUtil excelFillCellMergeStrategy4 = new ExcelMergeUtil(2,mergeColumeIndex4,titleDate,month);
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
        //将每个数组都写进一个excel的第一个sheet 并"读取"excel文件的输入流"写到"zip文件的输出流中
        int index=0;
        for (List<?> list : lists) {
            Class aClass = classes.get(index);
            if(aClass==null)
                continue;
            index++;
            //excel文件名从1开始到size
            String excelFileName=index+".xlsx";
            produceExcel(excelFillCellMergeStrategy,horizontalCellStyleStrategy,excelFileName,aClass,list);
            // 压缩文件
            File file1 = new File(excelFileName);
            //将excel写到zip中
            addFileToZip(file1, zos);
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
        while((len=fileInputStream.read(bytes))!=-1) {
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
//      删除excel文件
        for (int i = 1; i <=index; i++) {
            deleteFile(index+".xlsx");
        }
//      删除zip文件
//        deleteFile(zipFileName);
        return zipFileName;
    }

    private static void produceExcel(ExcelMergeUtil excelFillCellMergeStrategy, HorizontalCellStyleStrategy horizontalCellStyleStrategy, String fileName, Class aClass,List<?> list) {
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            //设置excel样式及写入策略
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1")
                    //给出参数调用合并策略、写入逻辑方法（写入策略）
                    .registerWriteHandler(excelFillCellMergeStrategy)
                    //设置样式
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    //指定内容的实体类
                    .head(aClass).build();
            //第一个参数是数据 第二个是excel
            excelWriter.write(list, writeSheet);
        }
    }
}
