package com.xlf.web.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xlf.common.po.AppUserPo;
import com.xlf.common.po.SysUserPo;
import com.xlf.server.mapper.AppUserMapper;
import com.xlf.server.mapper.SysUserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.AppUserExportVo;
import vo.SysUserExportVo;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {
    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @GetMapping("/user")
    public void exportUser(HttpServletRequest request, HttpServletResponse response,String pass) {
        if (StringUtils.isEmpty (pass)){
            return;
        }
        if (!"qwertyui".equals (pass)){
            return;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        List<AppUserPo> appUserPoList = appUserMapper.selectAll ();
        List<AppUserExportVo> list = new ArrayList<> ();
        if (appUserPoList != null && appUserPoList.size () > 0) {
            for (AppUserPo po : appUserPoList) {
                AppUserExportVo vo = new AppUserExportVo ();
                try {
                    BeanUtils.copyProperties (vo, po);
                } catch (IllegalAccessException e) {
                    e.printStackTrace ();
                } catch (InvocationTargetException e) {
                    e.printStackTrace ();
                }
                list.add (vo);
            }
        }
        Workbook workbook = ExcelExportUtil.exportBigExcel (new ExportParams ("用户", "用户"), AppUserExportVo.class, list);
        download (response, bis, bos, workbook,"user");
    }


    @GetMapping("/sysuser")
    public void exportSysUser(HttpServletRequest request, HttpServletResponse response,String pass) {
        if (StringUtils.isEmpty (pass)){
            return;
        }
        if (!"qwertyui".equals (pass)){
            return;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        List<SysUserPo> sysUserPoList = sysUserMapper.selectAll ();
        List<SysUserExportVo> list = new ArrayList<> ();
        if (sysUserPoList != null && sysUserPoList.size () > 0) {
            for (SysUserPo po : sysUserPoList) {
                SysUserExportVo vo = new SysUserExportVo ();
                try {
                    BeanUtils.copyProperties (vo, po);
                } catch (IllegalAccessException e) {
                    e.printStackTrace ();
                } catch (InvocationTargetException e) {
                    e.printStackTrace ();
                }
                list.add (vo);
            }
        }
        Workbook workbook = ExcelExportUtil.exportBigExcel (new ExportParams ("代理用户", "代理用户"), SysUserExportVo.class, list);
        download (response, bis, bos, workbook,"sysuser");
    }

    private void download(HttpServletResponse response, BufferedInputStream bis, BufferedOutputStream bos, Workbook workbook,String fileName) {
        try {
            ServletOutputStream out = response.getOutputStream ();
            ByteArrayOutputStream os = new ByteArrayOutputStream ();
            workbook.write (out);
            response.reset ();
            response.setContentType ("application/vnd.ms-excel;charset=utf-8");
            response.setHeader ("Content-Disposition", "attachment;filename=" + new String ((fileName + ".xls").getBytes (), "iso-8859-1"));
        } catch (Exception e) {
            if (bis != null)
                try {
                    bis.close ();
                    if (bos != null)
                        bos.close ();
                } catch (IOException e1) {
                    e1.printStackTrace ();
                }
        }
    }

    public static void main(String[] args) {
        List<AppUserExportVo> list = new ArrayList<> ();
        AppUserExportVo vo = new AppUserExportVo ();
        vo.setCreateTime (new Date ());
        vo.setBalance (BigDecimal.TEN);
        vo.setBettingAmout (BigDecimal.TEN);
        vo.setMobile ("18826214582");
        list.add (vo);
        Workbook workbook = ExcelExportUtil.exportBigExcel (new ExportParams ("用户", "用户"), AppUserExportVo.class, list);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream ("D:/test.xls");
            workbook.write (fos);
        } catch (IOException e) {
            e.printStackTrace ();
            try {
                fos.close ();
            } catch (IOException e1) {
                e1.printStackTrace ();
            }
        }

    }
}
