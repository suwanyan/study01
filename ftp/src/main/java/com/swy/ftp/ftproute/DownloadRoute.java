package com.swy.ftp.ftproute;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author suwanyan
 * @date 2021/1/12 15:33
 */
@Component
public class DownloadRoute extends RouteBuilder {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(DownloadRoute.class);

    @Value("${ftp.url}")
    private String sftpServer;

    @Value("${ftp.local-dir}")
    private String downloadLocation;

/*    @Autowired
    private DataProcessor dataProcessor;*/

    @Override
    public void configure() throws Exception{
        System.out.println(sftpServer);
        from(sftpServer)
                .to(downloadLocation)
                //.process(dataProcessor)
                .log(LoggingLevel.INFO, logger, "Download file ${file:name} complete.");
    }
}
