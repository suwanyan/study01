package com.swy.ftp.ftp;

import com.yx.scyj.constant.ParamConstant;
import com.yx.scyj.service.FirePointService;
import com.yx.scyj.service.WebSocketService;
import com.yx.scyj.util.FileUtil;
import com.yx.scyj.util.TimeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class DataProcessor implements Processor {

    @Value("${ftp.dir}")
    private String fileDir;

    @Value("${ftp.idxDirectory}")
    private String idxDirectory;

    @Autowired
    private ExecutorService processDataExecutor;

    @Autowired
    private FirePointService firePointService;

    private volatile String state;


    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getIn();
        if (!(message instanceof GenericFileMessage)) {
            return;
        }
        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();

        String fileName = inFileMessage.getGenericFile().getFileName();

        log.info("开始处理，文件路径【{}】", ParamConstant.FTP_DOWNLOAD_DIR);
        File file = new File(FileUtil.repalceBackslash(ParamConstant.FTP_DOWNLOAD_DIR + File.separator + fileName));
        //上传至本地,提交上传任务至线程池
        processDataExecutor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    //TODO 处理
                    firePointService.parseCsv(file);
                    state = "success";

                } catch (Exception e) {
                    log.error("处理" + fileName + "出错:" + e);
                    state = "fail";
                    e.printStackTrace();
                }
                if (state.equals("success")) {
                    log.info("文件【" + fileName + "】处理完成，向所有连接websocket的用户发送数据");
                    record(file, fileName);
                    //TODO 向前端发送数据
                    WebSocketService.BroadCastInfo();

                } else {
                    log.error("文件【" + fileName + "】处理失败");
                }
            }
        });
        log.info("文件【" +fileName + "】已提交至处理队列");
    }

    private void record(File file, String fileName) {
        //记录已处理过的文件
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(idxDirectory + File.separator + "scyj-" + TimeUtil.formatDate(new Date(), "yyyy-MM-dd") + ".idx");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(String.format("%s\t%d", fileName, file.length()));
        pw.flush();
        /*file.delete();
        file.getParentFile().delete();*/
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
