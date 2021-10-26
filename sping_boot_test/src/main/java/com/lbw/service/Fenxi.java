package com.lbw.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/31 9:53
 * @description：TODO
 */
public class Fenxi {
    public static void invokeShell(String command) throws Exception {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(command);
            int status = pro.waitFor();
            if (status != 0) {
                System.out.println("Failed to call shell's command");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                strbr.append(line).append("\n");
            }

            String result = strbr.toString();
            if (result.contains("ERROR")) {
                throw new Exception(command + "命令执行失败");
            }
            System.out.println(result);

        } catch (IOException | InterruptedException ec) {
            throw new Exception(command + "命令执行失败");
        }
    }

    /**
     *  调用plink
     * @param path snp地址
     * @return
     * @throws Exception
     */
    public String fenxi(String path) throws Exception {
        String shPath = "/root/application/dog_gene_ckg/plink/conversion_snp.sh";
        File file = new File(path);
        String fileName = file.getName();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String command = String.format("sh %s %s %s", shPath, fileName, id);
        invokeShell(command);
        return id;
    }

    /**
     * 调用gemma
     * @return
     * @throws Exception
     */
    public String kinship(String taskId) throws Exception {
        String shPath = "/root/application/dog_gene_ckg/plink/kinship.sh";
        String command = String.format("sh %s %s ", shPath,taskId);
        invokeShell(command);
        return taskId;
    }

    /**
     * 解析fam
     *
     * @param filePath 文件路径
     */
    public static List<String> analyzeFam(String filePath) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream, "GB2312");
            reader = new BufferedReader(inputStreamReader);
            String line = null;
            List<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 重新写入fam
     *
     * @param filePath
     * @param content
     */
    public static void writeFam(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  public void analysis(){
      String path = "";
      String id="";
      Scanner scanner = new Scanner(System.in);
      if (scanner.hasNext()) {
          path = scanner.next();
      }
      Fenxi test = new Fenxi();
      //执行plink
      try {
          id = test.fenxi(path);
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }

      //修改fam文件的第六列的表型数据
      String famPath="/tmp/zuyuanfenxi_"+id+"/gemma_input.fam";
      List<String> list = Fenxi.analyzeFam(famPath);
      StringBuffer su = new StringBuffer();
      for (String strings : list) {
          String substring = strings.substring(0, strings.length() - 2) + " 0";
          su.append(substring).append("\n");
      }
      Fenxi.writeFam(famPath, su.toString());
      //执行gemma 关联分析
      try {
          String kinship = test.kinship(id);
          String result="/root/application/dog_gene_ckg/plink/result/fenxi_"+kinship;
          System.out.println(result);
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }

  }

    public static void main(String[] args) {

    File file=new File("D:/int-yt/java/dog_genne");
        System.out.println(file.getName());
    }

}
