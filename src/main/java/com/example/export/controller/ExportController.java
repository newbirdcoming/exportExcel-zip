package com.example.export.controller;

import com.example.export.domain.OrderTable;
import com.example.export.domain.Result;
import com.example.export.domain.User;
import com.example.export.domain.UserDetail;
import com.example.export.domain.dto.UserAddOrderDto;
import com.example.export.service.OrderTableService;
import com.example.export.service.UserDetailService;
import com.example.export.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

import static com.example.export.utils.ExportMutiExcelToOneZipUtils.exportMutiExcelToZip;
import static com.example.export.utils.ExportMutiSheetToOneExcelUtils.exportMutiDtaToMutiSheetToOneExcel;
import static com.example.export.utils.ExportMutiSheetToOneZipUtilsModel.exportMutiDataToMutiExcelToOneZip;


/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName ExportController$
 * @description TODO
 * @date 2025/1/9 18:05
 **/

@RestController
@RequestMapping("/export")
@Api(tags = "导出接口")
public class ExportController {



    @Autowired
    private UserService userService;


    @Autowired
    private OrderTableService orderTableService;


    @Autowired
    private UserDetailService userDetailService;


    /**
    * @author Mr.Lan
    * @Description 获取任意个List 每个List作为一个excel 导出zip 返回zipName 没删除
    * @Date  2025/1/9 22:01
    * @Param []
    * @return com.example.export.domain.Result
    **/
    @ApiOperation(tags = "导出接口",value = "多个数据导出zip)")
    @PostMapping("/exportMutiExcelToZipToWeb")
    public Result<?> exportMutiExcelToZipToWeb(HttpServletResponse response) throws Exception {
        List<UserAddOrderDto> list=userService.pageList();
        String zipName = exportMutiDataToMutiExcelToOneZip(response, list, list);
        return Result.success("导出成功");
    }


    //将多个sheet写到一个excel 返回excelName 并在此删除excel
    @ApiOperation(tags = "导出接口",value = "多个数据导出excel)")
    @PostMapping("/exportMutiDateToExcelToWeb")
    public Result<?> exportMutiDateToExcelToWeb(HttpServletResponse response) throws Exception {
        List<UserAddOrderDto> list=userService.pageList();
        String excelName = exportMutiDtaToMutiSheetToOneExcel(response, list, list);
        //删除 因为为了合并zip 没有删除
        File file = new File(excelName);
        if (file.exists()) {
            file.delete();
        }
        return Result.success("导出成功");
    }


    //将任意个数据写进多个sheet的excel 写多个并返回excelName（没删除） 将多个的excel写到zip（删除excel 没删除zip）
    @ApiOperation(tags = "导出接口",value = "多个数据转多个excel导出zip)")
    @PostMapping("/exportMutiExcelToOneZipToWeb")
    public Result<?> exportMutiExcelToOneZipToWeb(HttpServletResponse response) throws Exception {
        List<UserAddOrderDto> list1=userService.pageList();
        List<OrderTable> list2 = orderTableService.list(null);
        List<UserDetail> list3 = userDetailService.list(null);
        List<User> list4 = userService.list(null);
        String excelName1 = exportMutiDtaToMutiSheetToOneExcel(response, list1, list2,list3,list4);
        String excelName2 = exportMutiDtaToMutiSheetToOneExcel(response, list1, list2,list3);
        String excelName3 = exportMutiDtaToMutiSheetToOneExcel(response, list1, list2,list3);
        String excelName4 = exportMutiDtaToMutiSheetToOneExcel(response, list1, list2,list3);
        exportMutiExcelToZip(response,excelName1,excelName2,excelName3,excelName4);
        return Result.success("导出成功");
    }


}
