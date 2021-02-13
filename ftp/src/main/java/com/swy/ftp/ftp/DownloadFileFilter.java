package com.swy.ftp.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import utils.TimeUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

@Slf4j
@Component
public class DownloadFileFilter implements GenericFileFilter<Object> {
    @Value("${ftp.dir}")
    private String downloadLocation;

    @Value("${ftp.idxDirectory}")
    private String idxDirectory;

    /**
     * 过滤下载文件
     *
     * @param file
     * @return true=下载，false=不下载
     */
    @Override
    public boolean accept(GenericFile<Object> file) {
        String fileName = file.getFileName();
        /*if (!fileName.endsWith("zip")) {
                return false;
        } else {*/
        boolean isInLocalDir = isInLocalDir(file.getFileName());
        if (isInLocalDir) {
            log.info("文件【" + file.getAbsoluteFilePath() + "】在本地文件夹中存在，已处理，不下载" + TimeUtil.formatDate(new Date(file.getLastModified())));
        } else {
            log.info("文件【" + file.getAbsoluteFilePath() + "】在本地文件夹中不存在，未处理，下载" + TimeUtil.formatDate(new Date(file.getLastModified())));
        }
        return !isInLocalDir;
        // }
    }

    /**
     * 文件是否已在本地目录中
     *
     * @param fileName
     * @return true=不存在   false=已存在了
     */
    private boolean isInLocalDir(String fileName) {
        try {
            File idxFile = new File(String.format("%s/scyj-%s.idx", idxDirectory, TimeUtil.formatDate(new Date(), "yyyy-MM-dd")));
            if (idxFile.exists()) {
                FileReader fr = new FileReader(idxFile);
                BufferedReader bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                while ((str = bf.readLine()) != null) {
                    String[] keys = str.split("\t");
                    if (str.split("\t")[0].equals(fileName)) {
                        return true;
                    }
                }
                bf.close();
                fr.close();
            }
            return false;
        } catch (Exception e) {
            log.error("获取本地已下载文件列表出错", e);
            return false;
        }
    }
}
