package shiro.mapper;

import org.springframework.stereotype.Component;
import shiro.model.User;

import java.util.List;

/**
 * @author 苏婉艳
 * @date 2020/8/14 19:10
 */
@Component
public interface UserMapper {
    int insert(User user);
    User findUserByEmail(String email);
    User findUserById(Integer userId);
    User findUserByName(String name);

    int countTelephone(String telephone);
    void deleteUserById(Integer userId);
    User checkCode(String code);
    void update(User user);

    //查询所有用户
    List<User> findAll();

    //分页查询
    List<User> findAllByPage();
}
