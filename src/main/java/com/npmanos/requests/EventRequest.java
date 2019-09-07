package com.npmanos.requests;

import com.npmanos.models.events.Alliance;
import com.npmanos.models.events.Award;
import com.npmanos.models.events.Event;
import com.npmanos.models.events.EventOPR;
import com.npmanos.models.events.EventRanking;
import com.npmanos.models.events.Insight;
import com.npmanos.models.events.SEvent;
import com.npmanos.models.matches.Match;
import com.npmanos.models.matches.SMatch;
import com.npmanos.models.teams.STeam;
import com.npmanos.models.teams.Team;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * In an attempt to keep this API organized, if you look at the blue alliance v3 documentation, all calls that start with /events/ or /event/
 * will be accessed from this class.
 *
 * API calls not implemented yet:
 * /event/{event_key}/district_points
 * /event/{event_key}/alliances
 * /event/{event_key}/insights
 * /event/{event_key}/rankings
 *
 * @since 1.0.0
 * @author Will Davies
 * @author Nick Manos
 */
public interface EventRequest {

    /**
     * Mirror of: /event/{event_key}/teams
     *
     * Gets a list of Team objects that competed in the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return the Response<List<Team>> array that this event includes
     */
    @GET("event/{eventKey}/teams")
    Response<List<Team>> getEventTeams(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/teams/simple
     *
     * Gets a list of Team objects that competed in the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return the Response<List<STeam>> array that this event includes (simple model)
     */
    @GET("event/{eventKey/teams")
    Response<List<STeam>> getSEventTeams(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/teams/keys
     *
     * Gets a list of Team keys that competed in the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < String>> containing all the team keys in this event
     */
    @GET("event/{eventKey}/teams/key")
    Response<List<String>> getTeamKeys(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /events/{year}
     *
     * Gets a list of events in the given year.
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return Response<List < Event>> containing all the events in the specified year
     */
    @GET("events/{year}")
    Response<List<Event>> getEvents(int year, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /events/{year}/simple
     *
     * Gets a list of events in the given year.
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return Response<List < SEvent>> containing all the events in the specified year
     */
    @GET("events/{year}/simple")
    Response<List<SEvent>> getSEvents(int year, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /events/{year}/keys
     *
     * Gets a list of event keys in the given year.
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return Response<List < String>> containing event keys for the specified year
     */
    @GET("events/{year}/keys")
    Response<List<String>> getEventKeys(int year, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}
     *
     * Gets an Event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<Event> model representing the event associated with the event key
     */
    @GET("events/{eventKey}")
    Response<Event> getEvent(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/simple
     *
     * Gets an Event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<SEvent> model representing the event associated with the event key
     */
    @GET("event/{eventKey}/simple")
    Response<SEvent> getSEvent(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/alliances
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return List of all alliances in this event
     */
    @GET("event/{eventKey}/alliances")
    Response<List<Alliance>> getEventAlliances(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/insights
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Insights for this event
     */
    @GET("event/{eventKey}/insights")
    Response<Insight> getEventInsights(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/oprs
     *
     * Gets a set of Event OPRs (including OPR, DPR, and CCWM) for the given Event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < EventOPR>> containing an EventOPR for each team
     */
    @GET("event/{eventKey}/oprs")
    Response<List<EventOPR>> getOprs(String eventKey, @Header("If-Modified-Since") String lastModified);

    /*
     * Mirror of: /event/{event_key}/predictions
     *
     * Gets information on TBA-generated predictions for the given Event. Contains year-specific information. WARNING This endpoint is currently under development and may change at any time.
     *
     * Not stable! No official model for this yet.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return JSON String containing prediction information
     */
    /*public String getPredictions(String eventKey) {
        String s =  (String) IO.doRequest("event/"+eventKey+"predictions");
        if(s == null) throw new DataNotFoundException("No predictions found for event with key: "+eventKey);
        return s;
    }*/

    /**
     * Mirror of: /event/{event_key}/matches
     *
     * Gets a list of matches for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < Match>> containing a Match object for each match in the specified event
     */
    @GET("event/{eventKey}/matches")
    Response<List<Match>> getMatches(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/matches/simple
     *
     * Gets a list of matches for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < SMatch>> containing a Match object for each match in the specified event
     */
    @GET("event/{eventKey}/matches/simple")
    Response<List<SMatch>> getSMatches(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/matches/keys
     *
     * GGets a list of match keys for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < String>> containing matches keys for the specified event
     */
    @GET("event/{eventKey}/matches/keys")
    Response<List<String>> getMatchKeys(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/awards
     *
     * Gets a list of awards from the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < Award>> containing all the awards won in this event
     */
    @GET("event/{eventKey}/awards")
    Response<List<Award>> getEventAwards(String eventKey, @Header("If-Modified-Since") String lastModified);

    /**
     * Mirror of: /event/{event_key}/rankings
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Response<List < EventRanking>> containing rankings of teams in this event
     */
    @GET("event/{eventKey}/rankings")
    Response<List<EventRanking>> getEventRankings(String eventKey, @Header("If-Modified-Since") String lastModified);

}
