package com.one_hundred_million.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author suwanyan
 * @date 2020/9/22 21:24
 */
@Data
public class Ticket {
    private int id;
    private int userId;
    private String idCard;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date ticketTime;
    private String startAddr;
    private String endAddr;
}
