package com.npmanos.requests;

import com.npmanos.models.districts.District;
import com.npmanos.models.events.Event;
import com.npmanos.models.events.SEvent;
import com.npmanos.models.teams.STeam;
import com.npmanos.models.teams.Team;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * In an attempt to keep this API organized, if you look at the blue alliance v3 documentation, all calls that start with /district/ or /districts/
 * will be accessed from this class.
 *
 * API calls not implemented yet:
 * /district/{district_key}/rankings
 *
 * @since 1.0.0
 * @author Will Davies
 * @author Nick Manos
 */
public interface DistrictRequest {

    /**
     * Mirror of: /district/{district_key}/teams
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @param lastModified
     * @return Response<List < Team>> including a Team object for every team in the specified district
     */
    @GET("district/{districtKey}/teams")
    Response<List<Team>> getDistrictTeams(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /district/{district_key}/teams/simple
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @param lastModified
     * @return Response<List < STeam>> including a STeam object for every team in the specified district (simple model)
     */
    @GET("district/{districtKey}/teams/simple")
    Response<List<STeam>> getDistrictSTeams(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /district/{district_key}/teams/keys
     *
     * Gets a list of Team objects that competed in events in the given district.
     * @param districtKey TBA District Key, eg 2016fim
     * @return Response<List < String>> containing all the team keys in this district
     */
    @GET("district/{districtKey}/teams/keys")
    Response<List<String>> getDistrictTeamKeys(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /district/{district_key}/events
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return Response<List < Event>> including an Event object for every event in the specified district
     */
    @GET("district/{districtKey}/events")
    Response<List<Event>> getDistrictEvents(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /district/{district_key}/events/simple
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return Response<List < SEvent>> including an SEvent object for every event in the specified district (simple model)
     */
    @GET("district/{districtKey}/events/simple")
    Response<List<SEvent>> getDistrictSEvents(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /district/{district_key}/events/keys
     *
     * Gets a list of Team objects that competed in events in the given district.
     * @param districtKey TBA District Key, eg 2016fim
     * @return Response<List < String>> containing all the team keys in this district
     */
    @GET("district/{districtKey}/events/keys")
    Response<List<String>> getDistrictEventKeys(String districtKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /districts/{year}
     *
     * Gets a list of districts and their corresponding district key, for the given year.
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return Response<List < District>> containing a District for each active district in the specified year
     */
    @GET("districts/{year}")
    Response<List<District>> getDistricts(int year, @Header("If-Modified-Since") String lastModified);

}
