package com.npmanos.requests;

import com.npmanos.models.matches.Match;
import com.npmanos.models.matches.SMatch;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * In an attempt to keep this API organized, if you look at the blue alliance v3 documentation, all calls that start with /match/
 * will be accessed from this class.
 *
 * @since 1.0.0
 * @author Will Davies
 */
public interface MatchRequest {

    /**
     * Mirror of: /match/{match_key}
     *
     * Gets a Match object for the given match key.
     * @param matchKey TBA Match Key, eg 2016nytr_qm1
     * @return {@code Response<Match>} object represented by the match key
     */
    @GET("match/{matchKey}")
    Response<Match> getMatch(String matchKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /match/{match_key}/simple
     *
     * Gets a Match object for the given match key.
     * @param matchKey TBA Match Key, eg 2016nytr_qm1
     * @return {@code Response<SMatch>} object represented by the match key (simple model)
     */
    @GET("match/{matchKey}/simple")
    Response<SMatch> getSMatch(String matchKey, @Header("If-Modified-Since") String lastModified);



}
