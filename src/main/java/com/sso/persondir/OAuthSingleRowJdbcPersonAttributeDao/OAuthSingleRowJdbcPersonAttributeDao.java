package com.sso.persondir.OAuthSingleRowJdbcPersonAttributeDao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class OAuthSingleRowJdbcPersonAttributeDao extends com.buession.cas.service.persondir.support.jdbc.OAuthSingleRowJdbcPersonAttributeDao {
	 
    @Override
    protected List<Map<String, Object>> query(ProviderId providerId) {
        final ParameterizedRowMapper<Map<String, Object>> rowMapper = getRowMapper();
        List<Map<String, Object>> results = null;
 
        if (providerId != null) {
            results = jdbcTemplate.query(queryTemplate, rowMapper, providerId.getProviderName(),
                    providerId.getId());
        } else {
            results = jdbcTemplate.query(queryTemplate, rowMapper);
        }
 
        return results;
    }
 
    /**
     * @param uid
     * @return ProviderId
     */
    @Override
    protected ProviderId convertAttributesMap(String uid) {
        String[] temp = uid.split("#");
        return temp.length >= 2 ? new ProviderId(temp[0].replace("Profile", "").toLowerCase(), temp[1]) : null;
    }
 
}
