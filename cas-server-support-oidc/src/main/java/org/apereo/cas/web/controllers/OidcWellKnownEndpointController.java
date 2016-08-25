package org.apereo.cas.web.controllers;

import com.google.common.collect.ImmutableList;
import org.apereo.cas.OidcConstants;
import org.apereo.cas.config.OidcServerDiscoverySettings;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This is {@link OidcWellKnownEndpointController}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class OidcWellKnownEndpointController {

    @Autowired
    private CasConfigurationProperties casProperties;
    
    /**
     * Gets well known discovery configuration.
     *
     * @return the well known discovery configuration
     */
    @RequestMapping(value = '/' + OidcConstants.BASE_OIDC_URL + "/.well-known", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OidcServerDiscoverySettings> getWellKnownDiscoveryConfiguration() {

        final OidcServerDiscoverySettings discoveryProperties =
                new OidcServerDiscoverySettings(casProperties.getServer().getPrefix(), casProperties.getAuthn().getOidc().getIssuer());

        discoveryProperties.setSupportedClaims(
                ImmutableList.of(OidcConstants.CLAIM_SUB, "name", OidcConstants.CLAIM_PREFERRED_USERNAME,
                        "family_name", "given_name", "middle_name", "given_name", "profile",
                        "picture", "nickname", "website", "zoneinfo", "locale", "updated_at",
                        "birthdate", "email", "email_verified", "phone_number",
                        "phone_number_verified", "address"));
        discoveryProperties.setSupportedScopes(OidcConstants.SCOPES);

        discoveryProperties.setSupportedResponseTypes(ImmutableList.of("code", "token"));
        discoveryProperties.setSupportedSubjectTypes(ImmutableList.of("public", "pairwise"));
        discoveryProperties.setSupportedClaimTypes(ImmutableList.of("normal"));

        discoveryProperties.setSupportedGrantTypes(ImmutableList.of("authorization_code", "password", "implicit"));
        return new ResponseEntity(discoveryProperties, HttpStatus.OK);
    }

    /**
     * Gets well known openid discovery configuration.
     *
     * @return the well known discovery configuration
     */
    @RequestMapping(value = '/' + OidcConstants.BASE_OIDC_URL + "/.well-known/openid-configuration", 
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OidcServerDiscoverySettings> getWellKnownOpenIdDiscoveryConfiguration() {
        return getWellKnownDiscoveryConfiguration();
    }
}
