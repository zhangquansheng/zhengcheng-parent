package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.satoken.utils.SaTokenUtil;
import com.zhengcheng.ums.domain.entity.SysUserEntity;
import com.zhengcheng.ums.service.SysPasswordService;
import com.zhengcheng.ums.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.date.LocalDateTimeUtil;

/**
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPasswordService passwordService;

    /**
     * 个人信息
     */
    @GetMapping(name = "个人信息管理-查询")
    public Result profile() {
        SysUserEntity user = userService.selectUserById(SaTokenUtil.getUserId());
        Map<String, String> userInfo = new HashMap<>(32);
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("userName", user.getUserName());
        userInfo.put("phonenumber", user.getPhonenumber());
        userInfo.put("email", user.getEmail());
        userInfo.put("createTime", LocalDateTimeUtil.formatNormal(user.getCreateTime()));
        userInfo.put("nickName", user.getNickName());
        userInfo.put("sex", user.getSex());
        Result ajax = Result.ok(userInfo);
        ajax.put("roleGroup", userService.selectUserRoleGroup(SaTokenUtil.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @PutMapping(name = "个人信息管理-修改")
    public Result updateProfile(@RequestBody SysUserEntity user) {
        SysUserEntity sysUser = userService.selectUserById(SaTokenUtil.getUserId());
        user.setUserId(sysUser.getUserId());
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !(userService.checkPhoneUnique(user))) {
            return Result.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !(userService.checkEmailUnique(user))) {
            return Result.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setPassword(null);
        user.setAvatar(null);
        if (userService.updateUserProfile(user) > 0) {
            return Result.ok();
        }
        return Result.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @PutMapping(value = "/updatePwd", name = "个人信息管理-重置密码")
    public Result updatePwd(String oldPassword, String newPassword) {
        SysUserEntity user = userService.selectUserById(SaTokenUtil.getUserId());
        String userName = user.getUserName();
        String password = user.getPassword();
        if (!passwordService.matches(oldPassword, password)) {
            return Result.error("修改密码失败，旧密码错误");
        }
        if (passwordService.matches(newPassword, password)) {
            return Result.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, passwordService.encode(newPassword)) > 0) {
            return Result.ok();
        }
        return Result.error("修改密码异常，请联系管理员");
    }
//
//    /**
//     * 头像上传
//     */
//    @PostMapping(value = "/avatar", name = "个人信息管理-头像上次")
//    public R avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
//        if (!file.isEmpty()) {
//            LoginUser loginUser = getLoginUser();
//            String avatar = FileUploadUtils.upload(ConfigExpander.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
//            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
//                R ajax = R.ok();
//                ajax.put("imgUrl", avatar);
//                // 更新缓存用户头像
//                loginUser.getUser().setAvatar(avatar);
//                tokenService.setLoginUser(loginUser);
//                return ajax;
//            }
//        }
//        return R.error("上传图片异常，请联系管理员");
//    }
}
