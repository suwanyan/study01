package shiro.model;

import lombok.Data;

import java.util.List;

/**
 * @author suwanyan
 * @date 2021/1/4 15:52
 */
@Data
public class Permission {
    private int id;
    private String name;
    private int pid;
    private String permission;
    private String description;
    private List<Menu> menus;
}
