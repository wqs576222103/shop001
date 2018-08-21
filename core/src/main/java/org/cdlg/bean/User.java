package org.cdlg.bean;

import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


/**
 * @Auther: wqs
 * @Date: 2018/8/19 0019 14:36
 * @Description: I LOVE IT？
 */
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    //@Column(name = "user_name")//数据库中的字段
    @NotEmpty(message = "用户名不能为空")//在验证是加入buildfail
    @Length(min = 5,max = 12,message = "用户名5到12位")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 5,max = 12,message = "密码5到12位")
    private String password;
    @NotEmpty(message = "电话号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$",message = "手机号码格式不合法")
    private String phone;
   //@NotEmpty(message = "邮箱不能为空")
  // @Email(message = "邮箱格式不合法")
    private String email;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
