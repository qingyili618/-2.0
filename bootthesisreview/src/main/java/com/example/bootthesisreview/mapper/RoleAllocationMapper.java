package com.example.bootthesisreview.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootthesisreview.entity.Role;
import com.example.bootthesisreview.entity.RoleAllocation;
import com.example.bootthesisreview.entity.StudentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleAllocationMapper extends BaseMapper<RoleAllocation> {
    @Select("select r.name " +
            "from roleallocation ra, role r " +
            "where ra.employee_id=#{id} and ra.role = r.id")
    List<Role> roleList(@Param("id") String id);//自己定义的方法

    @Select("select employee_id from roleallocation where role = 1")
    List<String> staffList();//自己定义的方法
}
