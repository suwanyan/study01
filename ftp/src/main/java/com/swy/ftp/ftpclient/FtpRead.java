package com.swy.ftp.ftpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author suwanyan
 * @date 2021/1/13 11:00
 */
@Slf4j
public class FtpRead {
    //传入ftp地址，端口，登录名，密码
    public FTPClient getFtpClient(String host, int port, String userName, String password) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);// 连接ftp
            ftp.login(userName, password);// 登陆ftp
            //判断ftp是否登录成功
            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                log.info("登陆成功" + host + ":" + port);
                return ftp;

            } else {
                log.info("登陆失败，用户名或者密码错误" + host + ":" + port);
                ftp.disconnect();//ftp关闭
                return null;
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.error("ftp地址可能错误");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ftp端口错误");
        }
        return null;

    }
    //传入ftp连接，和该链接下面的文件名称
    public String readFile(FTPClient ftp, String fileName) {
        StringBuffer sb = new StringBuffer();
        InputStream ps = null;
        try {
            ftp.setControlEncoding("UTF-8");
            ftp.changeWorkingDirectory(fileName);// 改变工作空间
            FTPFile[] ff = ftp.listFiles("/");//列出fileName下面所有文件

            for (int i = 0; i < ff.length; i++) {
                //如果该文件是文件夹，调用自身方法
                if (ff[i].isDirectory()) {
                    String s = readFile(ftp, ff[i].getName());
                    sb.append(s);
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    String date=dateFormat.format(new Date());
                    //System.out.println("当前时间"+date);
                    //读取以当天日期命名的txt文件
                    if (ff[i].getName().matches(date+".*.txt")) {
                        System.out.println(ff[i].getName());
                        //以二进制流的方式读取
                        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                        ftp.enterLocalPassiveMode();

                        ps = ftp.retrieveFileStream(ff[i].getName());
                        if (ps != null) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(ps));
                            String data = null;
                            sb.append(ff[i].getName());
                            try {
                                while ((data = br.readLine()) != null) {
                                    sb.append(data);
                                }
                                //关闭流
                                if (br != null) {
                                    br.close();
                                }
                                ftp.completePendingCommand();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("文件读取失败");
            e.printStackTrace();
        }

        return sb.toString();
    }

    //关闭ftp连接
    public static void closeFTPClient(FTPClient ftpClient) {
        try {
            ftpClient.disconnect();
            log.info("连接关闭");
        } catch (IOException e) {
            log.error("关闭ftp异常...", e);
        }
    }
    public static void main(String[] args) {
        FtpRead ftp = new FtpRead();

        FTPClient ftc = ftp.getFtpClient("36.112.11.166", 21, "ftp0001", "geovis@123");
        try {
            FTPFile[] ftf = ftc.listFiles("/");

            for(FTPFile  f:ftf){
                String s=ftp.readFile(ftc,"/"+ f.getName());
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {

            FtpRead.closeFTPClient(ftc);
        }
    }

}
