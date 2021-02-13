package shiro.model;

import lombok.Data;

import java.util.List;

/**
 * @author suwanyan
 * @date 2021/1/4 15:48
 */
@Data
public class Role {
    private int id;
    private String name;
    private String code;
    private String description;

    private List<Permission> permissions;
}
