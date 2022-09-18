package com.lbw.service;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author ：com.lbw
 * @date ：Created in 2022/1/13 15:32
 * @description：TODO
 */
@Data
public class MyExcelFillCellMergeStrategy implements CellWriteHandler {

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {


    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        //当前列
        int curColIndex = cell.getColumnIndex();
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        Sheet sheet = cell.getSheet();
        //当前行
        int curRowIndex = cell.getRowIndex();
        if (curRowIndex == 0) {
            Font font = workbook.createFont();
            font.setFontHeight((short) 200);
//            font.setBold(true);
        }
        //前两列
        if (curColIndex == 0 || curColIndex == 1) {
            sheet.setColumnWidth(curColIndex, 2500);
//        } else {
//            sheet.setColumnWidth(curColIndex, 6000);
//        }
            if (curRowIndex == 0) {

            }

        }

    }
}



