package com.mocredit.security.dao;

import com.mocredit.security.entity.Role;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Role:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role createRole(final Role role) {
        final String sql = "insert into sys_role(role, description, resource_ids, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, role.getRole());
                psst.setString(count++, role.getDescription());
                psst.setString(count++, role.getResourceIdsStr());
                psst.setBoolean(count++, role.getAvailable());
                return psst;
            }
        }, keyHolder);
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        final String sql = "update sys_role set role=?, description=?, resource_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                role.getRole(), role.getDescription(), role.getResourceIdsStr(), role.getAvailable(), role.getId());
        return role;
    }

    public void deleteRole(Long roleId) {
        final String sql = "delete from sys_role where id=?";
        jdbcTemplate.update(sql, roleId);
    }


    @Override
    public Role findOne(Long roleId) {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role where id=?";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), roleId);
        if (roleList.size() == 0) {
            return null;
        }
        return roleList.get(0);
    }

    @Override
    public List<Role> findAll() {
        final String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class));
    }

    public List<Long> findRoleIdByUserId(Long userId) {
        final String sql = "select group_concat(role_ids separator ',') as resourceIds from  sys_user_app_roles where user_id=" + userId;
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        List<Long> roleIdList = new ArrayList<Long>();
        if (!mapList.isEmpty()) {
            String resourceIds = mapList.get(0).get("resourceIds") + "";
            for (String roleId : resourceIds.split(",")) {
                if (roleId != null && !"".equals(roleId) && !"null".equals(roleId)) {
                    roleIdList.add(Long.valueOf(roleId));
                }
            }
        }
        return roleIdList;
    }

    public List<Role> findRoleByList(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "select id, role, description, resource_ids as resourceIdsStr, available from sys_role limit ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), offset, pageSize);
    }
}
