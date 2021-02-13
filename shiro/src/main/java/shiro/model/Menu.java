package shiro.model;

import lombok.Data;

/**
 * @author suwanyan
 * @date 2021/1/4 16:12
 */
@Data
public class Menu {
    private int id;
    private int pid;
    private String name;
    private String url;
    private String status;
}
