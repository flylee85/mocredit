package com.mocredit.security.dao;

import com.mocredit.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User createUser(final User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        final String ctime = sdf.format(date);
        final String sql = "insert into sys_user(username, password,md5_pwd, salt, locked,c_time) values(?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
//                psst.setLong(count++, user.getOrganizationId());
                psst.setString(count++, user.getUsername());
                psst.setString(count++, user.getPassword());
                psst.setString(count++, user.getMd5Pwd());
                psst.setString(count++, user.getSalt());
                psst.setBoolean(count++, user.getLocked());
                psst.setString(count++, ctime);
                return psst;
            }
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public User updateUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        String utime = sdf.format(date);
        String sql = "update sys_user set organization_id=?,username=?, password=?, md5_pwd=?, salt=?, locked=?,u_time=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getOrganizationId(), user.getUsername(), user.getPassword(), user.getMd5Pwd(), user.getSalt(), user.getLocked(), utime, user.getId());
        return user;
    }

    public void deleteUser(Long userId) {
        String sql = "delete from sys_user where id=?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public User findOne(Long userId) {
        String sql = "select id, organization_id, username, password, salt, locked from sys_user where id=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, organization_id, username, password, c_time as ctime,u_time as utime, salt, locked from sys_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
    }


    @Override
    public User findByUsername(String username) {
        String sql = "select id, organization_id, username, password,md5_pwd as md5Pwd, salt, locked from sys_user where username=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public List<User> findUserByList(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "select a.id, a.organization_id, a.username, a.password, a.c_time as ctime,a.u_time as utime, a.salt, a.locked,c.role as roleName from sys_user a LEFT JOIN  sys_user_app_roles b on a.id=b.user_id LEFT JOIN sys_role c on b.role_ids=c.id limit ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), offset, pageSize);
    }

    public int findUserCount() {
        String sql = "select count(1) from sys_user";
        return jdbcTemplate.queryForInt(sql);
    }
}
