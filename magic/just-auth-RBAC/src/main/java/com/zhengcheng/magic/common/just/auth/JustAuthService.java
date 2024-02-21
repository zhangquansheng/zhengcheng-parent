package com.zhengcheng.magic.common.just.auth;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xkcoding.http.config.HttpConfig;
import com.zhengcheng.magic.common.just.auth.cache.AuthStateRedisCache;
import com.zhengcheng.magic.common.just.auth.custom.AuthMyGitlabRequest;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.*;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;

/**
 * JustAuthService
 *
 * @author quansheng1.zhang
 * @since 2021/8/14 13:47
 */
@Component
public class JustAuthService {

    @Autowired
    private AuthStateRedisCache stateRedisCache;

    /**
     * 根据具体的授权来源，获取授权请求工具类
     *
     * @param source
     *            来源
     * @return AuthRequest
     */
    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/dingtalk").build());
                break;
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/baidu")
                    .scopes(Arrays.asList(AuthBaiduScope.BASIC.getScope(), AuthBaiduScope.SUPER_MSG.getScope(),
                        AuthBaiduScope.NETDISK.getScope()))
                    // .clientId("")
                    // .clientSecret("")
                    // .redirectUri("http://localhost:9001/oauth/baidu/callback")
                    .build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/github")
                    .scopes(AuthScopeUtils.getScopes(AuthGithubScope.values()))
                    // 针对国外平台配置代理
                    .httpConfig(HttpConfig.builder().timeout(15000)
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080))).build())
                    .build(), stateRedisCache);
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(
                    AuthConfig.builder().clientId("")
                        .clientSecret("")
                        .redirectUri("http://9e3e9871c434.ngrok.io/oauth/callback/gitee")
                        .scopes(AuthScopeUtils.getScopes(AuthGiteeScope.values())).build(),
                    stateRedisCache);
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/weibo")
                    .scopes(Arrays.asList(AuthWeiboScope.EMAIL.getScope(),
                        AuthWeiboScope.FRIENDSHIPS_GROUPS_READ.getScope(),
                        AuthWeiboScope.STATUSES_TO_ME_READ.getScope()))
                    .build());
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/oschina").build());
                break;
            case "alipay":
                // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1，所以这儿的回调地址使用的局域网内的ip
                authRequest = new AuthAlipayRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .alipayPublicKey("").redirectUri("https://www.zhyd.me/oauth/callback/alipay").build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/qq").build());
                break;
            case "wechat_open":
                authRequest = new AuthWeChatOpenRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://www.zhyd.me/oauth/callback/wechat").build());
                break;
            case "csdn":
                authRequest = new AuthCsdnRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/csdn").build());
                break;
            case "taobao":
                authRequest = new AuthTaobaoRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/taobao").build());
                break;
            case "google":
                authRequest =
                    new AuthGoogleRequest(AuthConfig.builder().clientId("").clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/google")
                        .scopes(AuthScopeUtils.getScopes(AuthGoogleScope.USER_EMAIL, AuthGoogleScope.USER_PROFILE,
                            AuthGoogleScope.USER_OPENID))
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder().timeout(15000)
                            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080))).build())
                        .build());
                break;
            case "facebook":
                authRequest =
                    new AuthFacebookRequest(AuthConfig.builder().clientId("").clientSecret("")
                        .redirectUri("https://justauth.cn/oauth/callback/facebook")
                        .scopes(AuthScopeUtils.getScopes(AuthFacebookScope.values()))
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder().timeout(15000)
                            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080))).build())
                        .build());
                break;
            case "douyin":
                authRequest = new AuthDouyinRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/douyin").build());
                break;
            case "linkedin":
                authRequest = new AuthLinkedinRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/linkedin").scopes(null).build());
                break;
            case "microsoft":
                authRequest = new AuthMicrosoftRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/microsoft")
                    .scopes(Arrays.asList(AuthMicrosoftScope.USER_READ.getScope(),
                        AuthMicrosoftScope.USER_READWRITE.getScope(), AuthMicrosoftScope.USER_READBASIC_ALL.getScope(),
                        AuthMicrosoftScope.USER_READ_ALL.getScope(), AuthMicrosoftScope.USER_READWRITE_ALL.getScope(),
                        AuthMicrosoftScope.USER_INVITE_ALL.getScope(), AuthMicrosoftScope.USER_EXPORT_ALL.getScope(),
                        AuthMicrosoftScope.USER_MANAGEIDENTITIES_ALL.getScope(),
                        AuthMicrosoftScope.FILES_READ.getScope()))
                    .build());
                break;
            case "mi":
                authRequest = new AuthMiRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/mi").build());
                break;
            case "toutiao":
                authRequest = new AuthToutiaoRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/toutiao").build());
                break;
            case "teambition":
                authRequest = new AuthTeambitionRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://127.0.0.1:8443/oauth/callback/teambition").build());
                break;
            case "pinterest":
                authRequest =
                    new AuthPinterestRequest(AuthConfig.builder().clientId("").clientSecret("")
                        .redirectUri("https://eadmin.innodev.com.cn/oauth/callback/pinterest")
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder().timeout(15000)
                            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080))).build())
                        .build());
                break;
            case "renren":
                authRequest = new AuthRenrenRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://127.0.0.1:8443/oauth/callback/teambition").build());
                break;
            case "stack_overflow":
                authRequest = new AuthStackOverflowRequest(AuthConfig.builder().clientId("").clientSecret("((")
                    .redirectUri("http://localhost:8443/oauth/callback/stack_overflow").stackOverflowKey("").build());
                break;
            case "huawei":
                authRequest = new AuthHuaweiRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://127.0.0.1:8443/oauth/callback/huawei")
                    .scopes(Arrays.asList(AuthHuaweiScope.BASE_PROFILE.getScope(),
                        AuthHuaweiScope.MOBILE_NUMBER.getScope(), AuthHuaweiScope.ACCOUNTLIST.getScope(),
                        AuthHuaweiScope.SCOPE_DRIVE_FILE.getScope(), AuthHuaweiScope.SCOPE_DRIVE_APPDATA.getScope()))
                    .build());
                break;
            case "wechat_enterprise":
                authRequest = new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://justauth.cn/oauth/callback/wechat_enterprise").agentId("1000003").build());
                break;
            case "kujiale":
                authRequest = new AuthKujialeRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/kujiale").build());
                break;
            case "gitlab":
                authRequest = new AuthGitlabRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/gitlab")
                    .scopes(AuthScopeUtils.getScopes(AuthGitlabScope.values())).build());
                break;
            case "meituan":
                authRequest = new AuthMeituanRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/meituan").build());
                break;
            case "eleme":
                authRequest = new AuthElemeRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://dblog-web.zhyd.me/oauth/callback/eleme").build());
                break;
            case "mygitlab":
                authRequest = new AuthMyGitlabRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://127.0.0.1:8443/oauth/callback/mygitlab").build());
                break;
            case "twitter":
                authRequest =
                    new AuthTwitterRequest(AuthConfig.builder().clientId("").clientSecret("")
                        .redirectUri("https://threelogin.31huiyi.com/oauth/callback/twitter")
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder().timeout(15000)
                            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080))).build())
                        .build());
                break;
            case "wechat_mp":
                authRequest =
                    new AuthWeChatMpRequest(AuthConfig.builder().clientId("").clientSecret("").redirectUri("").build());
                break;
            case "aliyun":
                authRequest = new AuthAliyunRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/aliyun").build());
                break;
            case "xmly":
                authRequest = new AuthXmlyRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/xmly").build());
                break;
            case "feishu":
                authRequest = new AuthFeishuRequest(AuthConfig.builder().clientId("").clientSecret("")
                    .redirectUri("http://localhost:8443/oauth/callback/feishu").build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }
}
