package shiro.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 苏婉艳
 * @date 2020/8/14 15:37
 */
@Data
public class User {
    private Integer userId;
    private String name;
    private String password;
    private String email;
    private String telephone;
    private int siteId;
    private String ip;
    private String code;
    //0未激活，1激活
    private String status;
    @ApiModelProperty(value = "注册时间", example = "2018-05-18 02:21:50", allowEmptyValue = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();
    private String user_type;

    private List<Role> roles;
}

