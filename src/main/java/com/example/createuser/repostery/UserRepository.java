package com.example.createuser.repostery;

import com.example.createuser.entity.UserLogin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

  @Insert("INSERT INTO users (username, password,email) VALUES ( #{username}, #{password}, #{email})")
    public int register(UserLogin userLogin);

  @Delete("DELETE FROM users WHERE userId = #{id}")
  public int deleteById(long id);

  @Update("Update users set username=#{username}, email=#{email},password=#{password} where userId=#{userId}")
  public int update(UserLogin userLogin);

  @Select("select * from users")
  public List< UserLogin > findAll();

  @Select("SELECT * FROM users WHERE userId = #{userId}")
  public UserLogin findById(int userId);

  @Select("SELECT * FROM users WHERE username = #{username}")
  public UserLogin findByUserName(String username);




}





