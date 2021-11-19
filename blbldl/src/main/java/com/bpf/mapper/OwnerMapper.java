package com.bpf.mapper;

import com.bpf.pojo.Owner;
import com.bpf.pojo.OwnerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OwnerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    long countByExample(OwnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int deleteByExample(OwnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int insert(Owner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int insertSelective(Owner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    List<Owner> selectByExample(OwnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    Owner selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int updateByExampleSelective(@Param("record") Owner record, @Param("example") OwnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int updateByExample(@Param("record") Owner record, @Param("example") OwnerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int updateByPrimaryKeySelective(Owner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table owner
     *
     * @mbg.generated Thu Nov 18 23:53:07 CST 2021
     */
    int updateByPrimaryKey(Owner record);
}