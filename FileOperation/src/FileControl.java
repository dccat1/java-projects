import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileControl {

    private static Log log = LogFactory.getLog(FileControl.class);
    private final static int LINE_CNT = 5;
    /**
     * 获取文件行数
     * @param file
     * @return
     */
    public int getFileLines(File file){
        int cnt = 0;
        LineNumberReader lnr = null;
        try
        {
            String line = "";
            lnr = new LineNumberReader(new FileReader(file));
            while (!(line = lnr.readLine()).isEmpty()){ }
            cnt = lnr.getLineNumber();
        } catch (FileNotFoundException e)
        {
            log.error("文件未找到");
        } catch (IOException e)
        {
            log.error("文件读取错误");
        }finally
        {
            try
            {
                lnr.close();
            } catch (IOException e)
            {
                log.error("文件关闭失败");
            }
        }
        return cnt;
    }

    /**
     * 获取系统时间
     * @return
     */
    public String getSysTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 打开，写入，关闭文件
     * @param file
     * @param str
     */
    public void fileOper(File file,String str){
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(str);
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e)
        {
            log.error("文件未找到");
        } catch (IOException e)
        {
            log.error("文件写入出错");
        }
    }

    /**
     * 创建新文件
     * @param partition
     * @param date
     */
    public void createNewFile(int partition,String date){
        int i = (int)Math.random()*1000;
        String id = String.format("%06d",i);
        String fileName = "FMS_"+partition+"_"+date+"_"+id;
        File file = new File("../files/"+fileName);
        if (!file.exists()){
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                log.error("文件创建出错");
            }
        }
    }

    /**
     * 移动文件
     * @param oldFile
     */
    public void move2NewDir(File oldFile){
        oldFile.renameTo(new File("/file_merge/"+oldFile.getName()));
        log.debug("文件转移成功");
        oldFile.delete();
        log.debug("原文件删除成功");
    }
}
