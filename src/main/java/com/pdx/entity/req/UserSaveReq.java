package com.pdx.entity.req;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-11 17:08
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveReq {

    private String id;
    @NotNull(message = "【用户名】不能为空")
    private String loginName;
    @NotNull(message = "【昵称】不能为空")
    private String name;
    @NotNull(message = "【密码】不能为空")
    @Length(min = 6,max = 100,message = "【密码】6—100位")
    private String password;

    private Boolean status;

    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
