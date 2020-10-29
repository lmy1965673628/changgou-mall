package com.changgou.token;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:42
 * @Description: com.changgou.token
 *      创建JWT令牌，使用私钥加密
 ****/
public class CreateJwtTest {

    /***
     * 创建令牌测试
     */
    @Test
    public void testCreateToken(){
        //证书文件路径
        String key_location="changgou.jks";
        //秘钥库密码
        String key_password="changgou";
        //秘钥密码
        String keypwd = "changgou";
        //秘钥别名
        String alias = "changgou";

        //访问证书路径 读取jks的文件
        ClassPathResource resource = new ClassPathResource(key_location);

        //创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,key_password.toCharArray());

        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypwd.toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();

        //自定义Payload
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "itheima");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");

        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));


        //取出令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.HjvJvLwpSOCPqHAfeCcYxCias-jAHpBOE3UenxSyHUU-bx6Kngce7taYlSPS3IUQ-N7mfzB-9nV8O6f26p7FjXRvXfVnxLhoVnlBVjPXtIrpLmJQFfKbnca4MD5mlUEbRfAwieBSKQUmCLgBtOQUGy1S5ShhLBksrLD6hG8azGGqUzYXcPnop4E794kvxdqo2LHkPxIcafiyEZ8l5XkeHZ3xLpDmiCEFv9Ml8G5w1JPZI7T5cYclydBoUQKZeWEpvqn6x6v-ISrL4b8mDwel9nbXikQUvA0tvhnQYR0GEF_0JPuL-UFs8DpvI28UABq0DcZH4rDpxMlVF72hA8MA-g";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnkill2y2jYgVRMc58FJa3gXe32VkztUAYJWyiHMPJPzKv0CmPyjT43PIwNiGsFogXAb6ITYkYxLqcWHgElQkgtqBiKHB8yYNftLMIN33UBQw1oV0w5A6FqJZ/grRm2bYR1unxHLs4m4Es1H3+PH6ND6I1PFHCY+oUpI1muHAhHpK2OJTpRjjJi47KuwjNhzULLi+0ksaqC4plD1NoCEOjQph0dXfoYyBR1OhK1Nc2byjq+sSFAQOQLKWOUxH0GBtE+8BkqfDt+hjUrxJmJqGyzRTdmvkTVeuee5k88Vid/wLIRZOQU/FUPoRTwhbniCRTgPYz6Q2KsDo4xPRxnuGcwIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
