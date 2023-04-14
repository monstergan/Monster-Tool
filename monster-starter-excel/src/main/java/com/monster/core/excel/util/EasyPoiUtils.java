package com.monster.core.excel.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 导出导入工具类
 */
public class EasyPoiUtils {

    private EasyPoiUtils() {

    }

    private static final String EXCEL_NOT_NULL = "excel文件不能为空";


    public static void sellerExportExcel(List<?> list, String title, String sheetName,
                                         Class<?> pojoClass, String fileName,
                                         HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名。创建表头
     *
     * @param list           导出的实体类
     * @param title          表头名称
     * @param sheetName      sheet表名
     * @param pojoClass      映射的实体类
     * @param isCreateHeader 是否创建表头
     * @param fileName       导出的文件名称
     * @param response       HttpServletResponse
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,
                                   String fileName, boolean isCreateHeader,
                                   HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        exportParams.setStyle(ExcelStyleUtils.class);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
     *
     * @param list      导出的实体类
     * @param title     表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param fileName  导出的文件名称
     * @param response  HttpServletResponse
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,
                                   String fileName, HttpServletResponse response,
                                   Integer userMasterId) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 功能描述：Map 集合导出
     *
     * @param list     实体集合
     * @param fileName 导出的文件名称
     * @param response HttpServletResponse
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list         导出的实体集合
     * @param fileName     导出的文件名
     * @param pojoClass    pojo实体
     * @param exportParams ExportParams封装实体
     * @param response     HttpServletResponse
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
                                      HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 功能描述：Excel导出
     *
     * @param fileName 文件名称
     * @param response HttpServletResponse
     * @param workbook Excel对象
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response,
                                      Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list     导出的实体集合
     * @param fileName 导出的文件名
     * @param response HttpServletResponse
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName,
                                      HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @param filePath   文件路径
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        File file = new File(filePath);
        try {
            list = ExcelImportUtil.importExcel(file, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 功能描述：根据文件流来导入Excel
     *
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass, int sheetNum) {
        //判断文件是否存在
        if (file == null) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setSheetNum(sheetNum);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @param file       上传的文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException(EXCEL_NOT_NULL);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());

        }
        return list;
    }

    /**
     * 功能描述: 解析excel并封装成相应的实体
     *
     * @param file:       上传的文件
     * @param titleRows:  表标题的行数
     * @param headerRows: 表头行数
     * @param pojoClass:  ReplenishmentStrategyExcel 实体类
     * @param needVerify: 是否开启验证
     * @param handler:    自定义验证处理类(可以为空)
     */

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass, boolean needVerify, IExcelVerifyHandler<T> handler) {
        if (file == null) {
            return Collections.emptyList();
        }
        ExcelImportResult<T> result;
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        //设置校验和自定义校验处理器
        params.setNeedVerify(needVerify);
        if (handler != null) {
            params.setVerifyHandler(handler);
        }
        List<T> list = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
            //校验失败返回错误信息
            if (result.isVerifyFail()) {
                throw new UnsupportedOperationException("excel校验失败:" + result.getFailList());
            }
            list = result.getList();
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException(EXCEL_NOT_NULL);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());

        }
        return list;
    }

    /**
     * 重载解析方法，增加是否分批校验参数
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass, boolean needVerify, IExcelVerifyHandler<T> handler, Boolean isVerifyFileSplit) {
        if (file == null) {
            return Collections.emptyList();
        }
        ExcelImportResult<T> result;
        ImportParams params = new ImportParams();
        params.setVerifyFileSplit(isVerifyFileSplit);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        //设置校验和自定义校验处理器
        params.setNeedVerify(needVerify);
        if (handler != null) {
            params.setVerifyHandler(handler);
        }
        List<T> list = null;
        try {
            result = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
            //校验失败返回错误信息
            if (result.isVerifyFail()) {
                throw new UnsupportedOperationException("excel校验失败:" + result.getFailList());
            }
            list = result.getList();
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException(EXCEL_NOT_NULL);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());

        }
        return list;
    }
}
