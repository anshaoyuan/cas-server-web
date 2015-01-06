package com.sso.web.flow;

import org.scribe.up.provider.OAuthProvider;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.jasig.cas.support.oauth.authentication.principal.OAuthCredentials;
import com.buession.oauth.profile.BaseOAuthProfile;

public class OAuthBindCheckAction extends com.buession.cas.web.flow.OAuthBindJdbcCheckAction {
	 
    @Override
    protected boolean valid(final OAuthProvider provider, final OAuthCredentials credentials) {
        if (credentials == null) {
            return false;
        }
 
        BaseOAuthProfile profile = (BaseOAuthProfile) credentials.getUserProfile();
        if (profile == null) {
            return false;
        }
 
        String providerName = provider.getType().replace("Provider", "").toLowerCase();
        try {
           return jdbcTemplate.queryForObject(sql, Integer.class, providerName,
                    profile.getId()) > 0;
        } catch (final IncorrectResultSizeDataAccessException e) {
        }
 
        return false;
    }
 
}