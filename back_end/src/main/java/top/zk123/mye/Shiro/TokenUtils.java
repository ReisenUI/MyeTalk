package top.zk123.mye.Shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.TokenInvalidException;

import java.io.Serializable;
import java.util.Date;

/**
 * 生成和验证 Token 的工具类.
 */
@Component
public class TokenUtils implements Serializable {
    private static final long serialVersionUID = -3L;
    /**
     * Token 有效时长 15*24 h
     **/
    private static final Long EXPIRATION = 15 * 24 * 60 * 60L;

    /**
     * 生成 Token 字符串
     *
     * @param user 用户信息
     * @return 生成的Token字符串 or null
     */
    public String createToken(User user) {
        try {
            // 生成 Token
            String token = Jwts.builder()
                    // 设置 Token 签发者 可选
                    .setIssuer("心情说")
                    // 根据用户Id设置 Token 的接受者
                    .setId(String.valueOf(user.getId()))
                    // 设置过期时间
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                    // 设置 Token 生成时间 可选
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    // 通过 claim 方法设置一个 key = role，value = userRole 的值
                    .claim("role", user.getRole())
                    .claim("permission", user.getPermission())
                    // 用户角色
                    // 设置加密密钥和加密算法，注意要用私钥加密且保证私钥不泄露
                    .signWith(SignatureAlgorithm.HS512, "Private Key")
                    .compact();
            return String.format("Bearer %s", token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证 Token ，并获取到用户名和用户权限信息
     *
     * @param token Token 字符串
     * @return sysUser 用户信息
     */
    public User validationToken(String token) {
        try {
            // 解密 Token，获取 Claims 主体
            Claims claims = Jwts.parser()
                    // 设置公钥解密，以为私钥是保密的，因此 Token 只能是自己生成的，如此来验证 Token
                    .setSigningKey("Private Key")
                    .parseClaimsJws(token).getBody();
            assert claims != null;
            User user = new User();
            // 获得用户信息
            user.setId(Long.parseLong(claims.getId()));
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Token 刷新
     *
     * @param token Token
     * @return String 新 Token 或者 null
     */
    public String refreshToken(String token) throws TokenInvalidException {
        try {
            // 解密 Token，获取 Claims 主体
            Claims claims = Jwts.parser()
                    // 公钥解密
                    .setSigningKey("Private Key")
                    .parseClaimsJws(token).getBody();
            assert claims != null;
            // Token 过期时间
            Date expiration = claims.getExpiration();
            // Case 1: 20分钟内过期则刷新 Token
            if (!expiration.before(new Date(System.currentTimeMillis() + 20 * 60 * 1000))) {
                return null;
            }
            User user = new User();
            user.setId(Long.parseLong(claims.getId()));
            return createToken(user);
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException();
        }
    }
}

