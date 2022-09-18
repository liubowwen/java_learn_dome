package lbw.Utility;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
class TableInfo {
    private String tblName;
    private String tblType;
    private String tblComment;
}

@Data
class TableFiled {
    private String field;
    private String type;
    private String length;
    private boolean isNull;
    private String key;
    private String defaultVal;
    private String extra;
    private String comment;
}

public class TableToWordUtil {
    public static String GEN_FILE_PATH = "E:/";
    private static String dbHost = "10.200.0.126";
    private static int dbPort = 33062;
    private static String dbName = "kc";
    private static String userName = "root";
    private static String password = "root";

    public static void main(String[] args) throws SQLException {
        DataSource ds = getDataSource();
        table2Word(ds, dbName, dbName + ".doc");
    }


    /**
     * 生成word文档
     *
     * @param ds：数据源
     * @param fileName：生成文件地址
     * @return: void
     */
    public static void table2Word(DataSource ds, String databaseName, String fileName) throws SQLException {
        List<TableInfo> tables = getTableInfos(ds, databaseName);
        Document document = new Document(PageSize.A4);
        try {
            File dir = new File(GEN_FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = GEN_FILE_PATH + File.separator + fileName;
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            file.createNewFile();

            // 写入文件信息
            RtfWriter2.getInstance(document, new FileOutputStream(fileName));
            document.open();

            gebTableInfoDesc(document, tables);
            genTableStructDesc(document, tables, ds);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printMsg("所有表【共%d个】已经处理完成", tables.size());
    }

    private static void gebTableInfoDesc(Document document, List<TableInfo> tables) throws DocumentException, FontFormatException {
        Paragraph ph = new Paragraph();
        Paragraph p = new Paragraph("表清单描述");
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);

        printMsg("产生表清单开始");
        Table table = new Table(2);
        int[] widths = new int[]{500, 900};
        table.setWidths(widths);
        table.setBorderWidth(1);
        table.setPadding(0);
        table.setSpacing(0);

        //添加表头行
        Cell headerCell = new Cell("表名");
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerCell.setBackgroundColor(new Color(192, 192, 192));
        table.addCell(headerCell);

        headerCell = new Cell("表描述");
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerCell.setBackgroundColor(new Color(192, 192, 192));
        table.addCell(headerCell);
        table.endHeaders();

        for (TableInfo tableInfo : tables) {
            addCell(table, tableInfo.getTblName());
            addCell(table, tableInfo.getTblComment());
        }
        document.add(table);
        printMsg("产生表清单结束");
    }

    private static void genTableStructDesc(Document document, List<TableInfo> tables, DataSource ds) throws DocumentException, SQLException, FontFormatException {
        Paragraph ph = new Paragraph();
        Paragraph p = new Paragraph("表结构描述");
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);

        printMsg("共需要处理%d个表", tables.size());
        int colNum = 9;
        //循环处理每一张表
        for (int i = 0; i < tables.size(); i++) {
            TableInfo tableInfo = tables.get(i);
            String tblName = tableInfo.getTblName();
            String tblComment = tableInfo.getTblComment();

            printMsg("处理%s表开始", tableInfo);
            //写入表说明
//                String tblTile = "" + (i + 1) + " 表名称:" + tblName + "（" + tblComment + "）";
//                Paragraph paragraph = new Paragraph(tblTile);
//                document.add(paragraph);

            List<TableFiled> fileds = getTableFields(ds, tables.get(i).getTblName());
            Table table = new Table(colNum);
            int[] widths = new int[]{160, 250, 350, 160, 80, 80, 160, 80, 80};
            table.setWidths(widths);
            table.setBorderWidth(1);
            table.setPadding(0);
            table.setSpacing(0);

            //添加表名行
            String tblInfo = StringUtils.isBlank(tblComment) ? tblName : String.format("%s(%s)", tblName, tblComment);
            Cell headerCell = new Cell(tblInfo);
            headerCell.setColspan(colNum);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setBackgroundColor(new Color(192, 192, 192));
            table.addCell(headerCell);

            //添加表头行
            addCell(table, "是否主键");
            addCell(table, "字段名");
            addCell(table, "字段描述");
            addCell(table, "数据类型");
            addCell(table, "长度");
            addCell(table, "可空");
            addCell(table, "约束");
            addCell(table, "缺省值");
            addCell(table, "备注");

            table.endHeaders();

            // 表格的主体
            for (int k = 0; k < fileds.size(); k++) {
                TableFiled field = fileds.get(k);
                addCell(table, field.getKey().equals("PRI") ? "是" : "否");
                addCell(table, field.getField());
                addCell(table, field.getComment());
                addCell(table, field.getType());
                addCell(table, field.getLength());
                addCell(table, field.isNull() ? "是" : "否");
                addCell(table, "");
                addCell(table, field.getDefaultVal());
                addCell(table, field.getExtra());
            }
            //生成表格
            document.add(table);
            printMsg("处理%s表结束", tableInfo);
        }
    }

    private static void addCell(Table table, String content, int width) {
        addCell(table, content, width, Element.ALIGN_LEFT);
    }

    private static void addCell(Table table, String content) {
        addCell(table, content, -1, Element.ALIGN_LEFT);
    }

    /**
     * 添加表头到表格
     *
     * @param table
     * @param content
     * @param width
     * @param align
     */
    private static void addCell(Table table, String content, int width, int align) {
        Cell cell = new Cell(content);
        if (width > 0)
            cell.setWidth(width);
        cell.setHorizontalAlignment(align);
        table.addCell(cell);
    }

    private static void printMsg(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    private static List<TableInfo> getTableInfos(DataSource ds, String databaseName) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TableInfo> list = new ArrayList<>();
        try {
            conn = ds.getConnection();
            String sql = "select TABLE_NAME,TABLE_TYPE,TABLE_COMMENT from information_schema.tables where table_schema =? order by table_name";

            stmt = conn.prepareStatement(sql);
            setParameters(stmt, Arrays.asList(databaseName));

            rs = stmt.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();

            while (rs.next()) {
                TableInfo row = new TableInfo();
                row.setTblName(rs.getString(1));
                row.setTblType(rs.getString(2));
                row.setTblComment(rs.getString(3));
                list.add(row);
            }
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
            JdbcUtils.close(conn);
        }
        return list;
    }

    private static List<TableFiled> getTableFields(DataSource ds, String tblName) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TableFiled> list= new ArrayList<>();
         try {
            conn = ds.getConnection();
            //返回的列顺序是: Field,Type,Collation,Null,Key,Default,Extra,Privileges,Comment
            String sql = "SHOW FULL FIELDS FROM " + tblName;
            //返回的列顺序是: Field,Type,Null,Key,Default,Extra
//            sql = "show columns FROM " + tblName;

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();

            while (rs.next()) {
                TableFiled field = new TableFiled();
                field.setField(rs.getString(1));
                String type = rs.getString(2);
                String length = "";
                if (type.contains("(")) {
                    int idx = type.indexOf("(");
                    length = type.substring(idx + 1, type.length() - 1);
                    type = type.substring(0, idx);
                }
                field.setType(type);
                field.setLength(length);
                field.setNull(rs.getString(4).equalsIgnoreCase("YES") ? true : false);
                field.setKey(rs.getString(5));
                field.setDefaultVal(rs.getString(6));
                field.setExtra(rs.getString(7));
                field.setComment(rs.getString(9));
                list.add(field);
            }
        } finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(stmt);
            JdbcUtils.close(conn);
        }
        return list;
    }

    private static void setParameters(PreparedStatement stmt, List<Object> parameters) throws SQLException {
        for (int i = 0, size = parameters.size(); i < size; ++i) {
            Object param = parameters.get(i);
            stmt.setObject(i + 1, param);
        }
    }

    private static DataSource getDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&useSSL=false", dbHost, dbPort, dbName));
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setInitialSize(1);
        datasource.setMinIdle(1);
        datasource.setMaxActive(3);
        datasource.setMaxWait(60000);
        return datasource;
    }
}
