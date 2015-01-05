package com.sso.web.flow.OAuthBindAction;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.support.oauth.authentication.principal.OAuthCredentials;
import org.scribe.up.provider.OAuthProvider;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.buession.cas.authentication.handler.PasswordEncoder;
import com.buession.oauth.profile.BaseOAuthProfile;
import com.esotericsoftware.kryo.NotNull;

public class OAuthBindAction extends com.buession.cas.web.flow.OAuthBindAction {
	 
    @NotNull
    private DataSourceTransactionManager transactionManager;
 
    @NotNull
    private PasswordEncoder passwordEncoder;
 
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }
 
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
 
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
 
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
 
    @Override
    protected boolean bind(final HttpServletRequest request, final OAuthProvider provider,
            final OAuthCredentials credentials) {
        if (credentials == null) {
            return false;
        }
 
        BaseOAuthProfile profile = (BaseOAuthProfile) credentials.getUserProfile();
        if (profile == null) {
            return false;
        }
 
        TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager());
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            public Boolean doInTransaction(TransactionStatus status) {
                BaseOAuthProfile profile = (BaseOAuthProfile) credentials.getUserProfile();
                if (profile == null) {
                    return false;
                }

                return false;
            }
        });
    }
 
}