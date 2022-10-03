package com.healthMedical.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class poiRest {
    @Test
    public void test() throws IOException {
        //获取excel文件
        XSSFWorkbook excel=new XSSFWorkbook(new FileInputStream("C:\\Users\\FanJiangcheng\\Desktop\\test.xlsx"));
        //获取sheet页
        XSSFSheet sheet=excel.getSheetAt(0);
        //遍历sheet
        for(Row row:sheet){
            for(Cell cell:row){
                System.out.print(cell);
            }
            System.out.println();
        }
        //关闭资源
        excel.close();
    }
    //所有POI向excel文件写入数据，并且提供IO流将创建的Excel文件保存到本地
    @Test
    public void test2() throws IOException {
        //创建一个Excel文件
        //相当于在内存中创建一个excel文件（默认无工资表）
        XSSFWorkbook excel=new XSSFWorkbook();
        //创建工作表
        XSSFSheet sheet= excel.createSheet("健康医疗");

        //在工作表创建行
        XSSFRow title=sheet.createRow(0);
        //在行中创建单元格对象
        title.createCell(0).setCellValue("姓名");

        //在工作表创建行
        XSSFRow data=sheet.createRow(1);
        //在行中创建单元格对象
        data.createCell(0).setCellValue("姓名1");

        //创建输出流，将内存中的文件写到磁盘
        FileOutputStream out=new FileOutputStream(new File("d:\\hello.xlsx"));
        excel.write(out);
        out.flush();
        out.close();
        excel.close();
    }
}
