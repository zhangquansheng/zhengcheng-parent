package com.zhengcheng.ums.controller.facade.internal;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.ratelimit.annotation.RateLimiter;
import com.zhengcheng.ratelimit.enums.LimitType;
import com.zhengcheng.ums.controller.facade.OauthFacade;
import com.zhengcheng.ums.domain.entity.User;
import com.zhengcheng.ums.domain.entity.UserLoginLog;
import com.zhengcheng.ums.domain.enums.LoginResultEnum;
import com.zhengcheng.ums.domain.enums.LoginTypeEnum;
import com.zhengcheng.ums.dto.command.SendSmsCommand;
import com.zhengcheng.ums.dto.command.UserLoginCommand;
import com.zhengcheng.ums.dto.command.UserMobileLoginCommand;
import com.zhengcheng.ums.service.UserLoginLogService;
import com.zhengcheng.ums.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import static cn.hutool.core.date.DatePattern.PURE_DATE_FORMAT;

/**
 * UserFacadeImpl
 *
 * @author quansheng1.zhang
 * @since 2021/7/15 14:28
 */
@Slf4j
@Service
public class OauthFacadeImpl implements OauthFacade {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private Kaptcha kaptcha;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SaTokenInfo login(UserLoginCommand userLoginCommand, HttpServletRequest request) {
        kaptcha.validateByRedis(userLoginCommand.getUuid(), userLoginCommand.getCode());

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userLoginCommand.getUsername()));
        if (Objects.isNull(user)) {
            throw new BizException("用户名或密码错误！");
        }
        if (!userService.matches(userLoginCommand.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误！");
        }

        return userLogin(user, ServletUtil.getClientIP(request));
    }

    private String getMobileLoginKey(String mobile) {
        return "c:u:l:" + mobile;
    }

    private String getMobileSendNumKey(String mobile) {
        return "c:u:l:s:" + mobile + DateUtil.format(new Date(), "yyyyMMdd");
    }


    @RateLimiter(count = 2, time = 60, timeUnit = TimeUnit.SECONDS, limitType = LimitType.ARGS, message = "正在登陆中，请稍后重试！", keys = "#userMobileLoginCommand.mobile")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public SaTokenInfo login(UserMobileLoginCommand userMobileLoginCommand, HttpServletRequest request) {
        // 检验验证码
        String key = getMobileLoginKey(userMobileLoginCommand.getMobile());
        String code = redisTemplate.opsForValue().get(key);
        // 前端对 code md5加密了
        if (StrUtil.isBlank(code) || !userMobileLoginCommand.getCode().equals(SecureUtil.md5(code))) {
            throw new BizException("验证码不正确");
        }

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userMobileLoginCommand.getMobile()));
        if (Objects.isNull(user)) {
            // 注册新用户
            user = new User();
            // 手机注册的用户
            user.setSource("mobile");
            user.setName(userMobileLoginCommand.getMobile());
            user.setUsername(userMobileLoginCommand.getMobile());
            user.setMobile(userMobileLoginCommand.getMobile());
            user.setUserNo(RandomUtil.randomString(6));
            user.setEnable(Boolean.TRUE);
            userService.save(user);
        }

        SaTokenInfo saTokenInfo = userLogin(user, ServletUtil.getClientIP(request));

        // 删除缓存的验证码
        redisTemplate.delete(key);
        return saTokenInfo;
    }

    @RateLimiter(count = 2, time = 60, timeUnit = TimeUnit.SECONDS, limitType = LimitType.IP, message = "发送验证码太频繁，请稍后重试！")
    @Override
    public void sendLoginSms(SendSmsCommand sendSmsCommand) throws UnsupportedEncodingException {
        // 获取当天发送短信验证码的次数
        String sendNumKey = getMobileSendNumKey(sendSmsCommand.getMobile());
        String sendNumStr = redisTemplate.opsForValue().get(sendNumKey);
        if (StrUtil.isNotBlank(sendNumStr) && Objects.nonNull(sendNumStr) && Integer.parseInt(sendNumStr) >= 5) {
            throw new BizException("验证码请求今日已达上线");
        }

        String key = getMobileLoginKey(sendSmsCommand.getMobile());
        String existingCode = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(existingCode)) {
            throw new BizException("获取验证码请求太频繁，请稍后重试！");
        }

        String code = RandomUtil.randomNumbers(6);
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        log.info("发送手机号{}，验证码: {}", sendSmsCommand.getMobile(), code);

        Map<String, Object> param = new HashMap<>();
        param.put("uid", "175739");
        param.put("pwd", SecureUtil.md5("9wd02x"));
        param.put("mobile", sendSmsCommand.getMobile());
        param.put("srcphone", "75739");
        param.put("msg", URLEncoder.encode(StrUtil.format("【东证期货】验证码{}，感谢您参与本次竞答活动。如非本人操作，请忽略此短信。", code), "UTF-8"));
        String url = "http://bst.8315.cn:9892/cmppweb/sendsms";
        String res = HttpUtil.post(url, param);

        int sendNum = Objects.nonNull(sendNumStr) ? Integer.parseInt(sendNumStr) + 1 : 1;
        redisTemplate.opsForValue().set(sendNumKey, sendNum + "", 1, TimeUnit.DAYS);

        log.info("发送验证码的接口返回结果: {}", res);
    }

    /**
     * 用户登录
     *
     * @param user    用户
     * @param loginIp 登录IP
     * @return SaTokenInfo
     */
    private SaTokenInfo userLogin(User user, String loginIp) {
        if (Boolean.FALSE.equals(user.getEnable())) {
            userLoginLogService.save(UserLoginLog.builder().userId(user.getId()).type(LoginTypeEnum.LOGIN).serverIp(NetUtil.getLocalhostStr()).loginIp(loginIp).result(LoginResultEnum.FAILURE).content("用户已被禁用！").build());
            throw new BizException("用户已被禁用！");
        }

        // sa-token.dev33.cn 登录
        StpUtil.login(user.getId());

        user.setLastLogin(LocalDateTime.now());
        userService.updateById(user);

        String userActivityKeyPrefix = "zc:user:act:bm:";
        String dayKey = StrUtil.format("{}{}", userActivityKeyPrefix, DateUtil.format(new Date(), PURE_DATE_FORMAT));
        // 每天按日期生成一个位图
        stringRedisTemplate.opsForValue().setBit(dayKey, user.getId(), Boolean.TRUE);

        // 日活
        Long dayActivityNum = stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(dayKey.getBytes()));
        log.info("用户日活: {}", dayActivityNum);

        // 月活
        int maxDayNum = 30;
        byte[][] bytes = new byte[maxDayNum][];
        for (int offset = 0; offset < maxDayNum; offset++) {
            bytes[offset] = StrUtil.format("{}{}", userActivityKeyPrefix, DateUtil.format(DateUtil.offsetDay(new Date(), -offset), PURE_DATE_FORMAT)).getBytes();
        }
        String monthKey = StrUtil.format("{}{}", userActivityKeyPrefix, 30);
        stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.OR, monthKey.getBytes(), bytes));
        Long monthActivityNum = stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(monthKey.getBytes()));
        log.info("用户月活：{}", monthActivityNum);

        userLoginLogService.save(UserLoginLog.builder().userId(user.getId()).type(LoginTypeEnum.LOGIN).serverIp(NetUtil.getLocalhostStr()).loginIp(loginIp).result(LoginResultEnum.SUCCESS).build());
        return StpUtil.getTokenInfo();
    }

}
