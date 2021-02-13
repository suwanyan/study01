package com.swy.ftp.ftp;

import com.yx.scyj.constant.ParamConstant;
import com.yx.scyj.util.FileUtil;
import com.yx.scyj.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;

@Slf4j
@Component
public class DownloadRoute extends RouteBuilder {
	
    @Value("${ftp.url}")
    private String url;

    @Value("${ftp.local-dir}")
    private String localDir;

    @Value("${ftp.dir}")
    private String fileDir;

    @Value("${ftp.options}")
    private String options;

    @Value("${ftp.file-options}")
    private String fileOptions;

    @Autowired
    private DataProcessor dataProcessor;
    
    @Override
    public void configure() throws Exception {
    	
        String ftpServer = String.format("%s?%s", url, options);
        String date = TimeUtil.formatDate(new Date(), "yyyyMMdd");
        String downloadLocation = String.format("%s?%s", FileUtil.repalceBackslash(localDir + File.separator + date), fileOptions);
        reflect(FileUtil.repalceBackslash(fileDir + File.separator + date));
        from(ftpServer)
                .to(downloadLocation)
                .process(dataProcessor)
                .log(LoggingLevel.INFO, log, "文件【${file:name}】下载完成");
        log.info("ftp路径从【{}】到【{}】", ftpServer, downloadLocation);
    }

    private void reflect (String ftpDownloadDir) {
        try {
            Field field = ParamConstant.class.getDeclaredField("FTP_DOWNLOAD_DIR");
            field.set(null, ftpDownloadDir);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
