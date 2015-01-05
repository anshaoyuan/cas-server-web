package com.domain.authentication.handler;

import java.util.Map;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.buession.cas.authentication.handler.PasswordEncoder;
import com.buession.mcrypt.Mcrypt;

class DatabaseAuthenticationHandler extends com.buession.cas.authentication.handler.support.DatabaseQueryAuthenticationHandler {
	 
    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials)
            throws AuthenticationException {
        PasswordEncoder passwordEncoder = (PasswordEncoder) getPasswordEncoder();
        String username = getPrincipalNameTransformer().transform(credentials.getUsername());
        String password = credentials.getPassword();
        return true;
       // (1)、(2)
/*        try {
            final Map<String, Object> r = jdbcTemplate.queryForMap(sql, username);
            if (valid(username, password, r, passwordEncoder) == true) {
                 *//**
                   * 对旧用户数据更换加密方式，且重新生成密码
                 *//*
                if (Mcrypt.MD5.equals(r.get("algo")) == true) {
                    modifyEncoder((String) r.get("username"), credentials.getPassword(),
                            (String) r.get("salt"));
                }
                return true;
            }
        } catch (IncorrectResultSizeDataAccessException e) {
        	System.out.println("111");
        }*/
 /*
        (3)
        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, username, username,
                    username);
 
            if (result != null && result.size() > 0) {
                for (Map<String, Object> r : result) {
                    if (valid(username, password, r, passwordEncoder) == true) {
                        *//**
                         * 对旧用户数据更换加密方式，且重新生成密码
                         *//*
                        if (Mcrypt.MD5.equals(r.get("algo")) == true) {
                            modifyEncoder((String) r.get("username"), credentials.getPassword(),
                                    (String) r.get("salt"));
                        }
                        return true;
                    }
                }
            }
        } catch (IncorrectResultSizeDataAccessException e) {
        }
 */
      //  return false;
    }
 
    private boolean valid(String username, String password, final Map<String, Object> object,
            PasswordEncoder passwordEncoder) {
        String salt = (String) object.get("salt");
        String algo = (String) object.get("algo");
 
        passwordEncoder.setAlgo(algo);
 
        /**
         * 如果加密方式为 "MD5"，则为老的加密方式
         * 旧系统中，密码为明文传输，在后端 MD5 加密的，
         * 现在密码在前端进行了 MD5 加密传输，所以无需双重加密，而直接 encode(password+salt)
         */
        if (Mcrypt.MD5.equals(algo) == true) {
            password += salt;
        } else {
            passwordEncoder.setSalt(salt);
        }
 
        final String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword != null && encodedPassword.equalsIgnoreCase((String) object.get("password"));
    }
 
    private void modifyEncoder(final String username, final String password, final String salt) {
        PasswordEncoder passwordEncoder = (PasswordEncoder) getPasswordEncoder();
 
        passwordEncoder.setAlgo(Mcrypt.SHA512);
        passwordEncoder.setSalt(salt);
 
        String sql = "UPDATE `member` SET `algo` = ?, `password` = ? WHERE `username` = ?";
        jdbcTemplate.update(sql, Mcrypt.SHA512, passwordEncoder.encode(password), username);
    }
}
