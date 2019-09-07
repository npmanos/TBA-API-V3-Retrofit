package com.npmanos.main;

import com.npmanos.models.APIStatus;
import com.npmanos.models.districts.District;
import com.npmanos.models.events.Alliance;
import com.npmanos.models.events.Award;
import com.npmanos.models.events.Event;
import com.npmanos.models.events.EventOPR;
import com.npmanos.models.events.EventRanking;
import com.npmanos.models.events.Insight;
import com.npmanos.models.events.Media;
import com.npmanos.models.events.SEvent;
import com.npmanos.models.matches.Match;
import com.npmanos.models.matches.SMatch;
import com.npmanos.models.teams.Robot;
import com.npmanos.models.teams.STeam;
import com.npmanos.models.teams.Team;
import com.npmanos.requests.DistrictRequest;
import com.npmanos.requests.EventRequest;
import com.npmanos.requests.MatchRequest;
import com.npmanos.requests.OtherRequest;
import com.npmanos.requests.TeamRequest;
import com.npmanos.sorting.Sortable;
import com.npmanos.sorting.SortingType;
import com.npmanos.utils.Result;
import com.npmanos.utils.RetrofitIO;

import java.util.Arrays;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This is the com.npmanos.main interface for the API, let's talk about that.
 *
 * The actual API calls are organized by the type of request (district, event, match, team, other), which can be found
 * in the com.npmanos.requests package. Essentially, TBA is a single class that allows you to call every single method without having
 * to worry about what type it is. However, for debugging, it's nice to keep the actual API code organized by type.
 * You'll notice that TBA doesn't have any getters/setters/constructors for parameters, even though only a couple parameters are
 * shared across the entire API. There are several reasons for this, 1) to keep the Java implementation more consistent with the
 * online API 2) if you need to change parameters or input different ones for each method calls, it's nice to avoid having to
 * manually setting them with setters and the like. However, if you'd like a constructor/setter/getter implementation, check out the CTBA class.
 *
 * @since 1.0.0
 * @author Will Davies
 */
@SuppressWarnings("unused")
public class TBA {

    private Retrofit retrofitConfig;
    private DistrictRequest dr;
    private EventRequest er;
    private MatchRequest mr;
    private OtherRequest or;
    private TeamRequest tr;

    public TBA() {
        retrofitConfig = RetrofitIO.getRetrofitConfig();
        dr = retrofitConfig.create(DistrictRequest.class);
        er = retrofitConfig.create(EventRequest.class);
        mr = retrofitConfig.create(MatchRequest.class);
        or = retrofitConfig.create(OtherRequest.class);
        tr = retrofitConfig.create(TeamRequest.class);
    }

    /**
     * Sets the authentication token for the API, required for all calls!
     * Obtain an auth token from your account page on thebluealliance.com
     * @param authToken the auth token to set
     */
    public static void setAuthToken(String authToken) {
        Constants.AUTH_TOKEN = authToken;
    }

    /*
     * Sorting functions
     */

    public static <T extends Sortable> void sort(T[] array, SortingType type, boolean ascending) {
        Arrays.sort(array, (o1, o2) -> ascending ? o1.sort(type, ascending, o2) : o2.sort(type, ascending, o1));
    }

    public static <T extends Sortable> void sort(T[] array) {
        sort(array, SortingType.DEFAULT, true);
    }

    public static <T extends Sortable> void sort(T[] array, SortingType type) {
        sort(array, type, true);
    }

    private static <T> Result<T> map(Response<T> response) {
        if (response.isSuccessful()) {
            String lastModified = response.headers().get("Last-Modified");

            return new Result.Success<>(response.body(), lastModified);
        } else if (response.code() == 304) {
            return new Result.NotModified<>();
        } else {
            return new Result.Error<>(response.message());
        }
    }

    /**
     * Mirror of: /district/{district_key}/teams
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return Team[] including a Team object for every team in the specified district
     */
    public Result<List<Team>> getDistrictTeams(String districtKey) {
        return getDistrictTeams(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/teams
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @param lastModified
     * @return Team[] including a Team object for every team in the specified district
     */
    public Result<List<Team>> getDistrictTeams(String districtKey, String lastModified) {
        return map(dr.getDistrictTeams(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/teams/simple
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return STeam[] including a STeam object for every team in the specified district (simple model)
     */
    public Result<List<STeam>> getDistrictSTeams(String districtKey) {
        return getDistrictSTeams(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/teams/simple
     *
     * @param districtKey  TBA District Key, eg 2016fim
     * @param lastModified
     * @return STeam[] including a STeam object for every team in the specified district (simple model)
     */
    public Result<List<STeam>> getDistrictSTeams(String districtKey, String lastModified) {
        return map(dr.getDistrictSTeams(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/teams/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictTeamKeys(String districtKey) {
        return getDistrictTeamKeys(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/teams/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     * @param districtKey  TBA District Key, eg 2016fim
     * @param lastModified
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictTeamKeys(String districtKey, String lastModified) {
        return map(dr.getDistrictTeamKeys(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return Event[] including an Event object for every event in the specified district
     */
    public Result<List<Event>> getDistrictEvents(String districtKey) {
        return getDistrictEvents(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/events
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @param lastModified
     * @return Event[] including an Event object for every event in the specified district
     */
    public Result<List<Event>> getDistrictEvents(String districtKey, String lastModified) {
        return map(dr.getDistrictEvents(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events/simple
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return SEvent[] including an SEvent object for every event in the specified district (simple model)
     */
    public Result<List<SEvent>> getDistrictSEvents(String districtKey) {
        return getDistrictSEvents(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/events/simple
     *
     * @param districtKey  TBA District Key, eg 2016fim
     * @param lastModified
     * @return SEvent[] including an SEvent object for every event in the specified district (simple model)
     */
    public Result<List<SEvent>> getDistrictSEvents(String districtKey, String lastModified) {
        return map(dr.getDistrictSEvents(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     * @param districtKey TBA District Key, eg 2016fim
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictEventKeys(String districtKey) {
        return getDistrictEventKeys(districtKey, null);
    }

    /**
     * Mirror of: /district/{district_key}/events/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     * @param districtKey  TBA District Key, eg 2016fim
     * @param lastModified
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictEventKeys(String districtKey, String lastModified) {
        return map(dr.getDistrictEventKeys(districtKey, lastModified));
    }

    /**
     * Mirror of: /districts/{year}
     * <p>
     * Gets a list of districts and their corresponding district key, for the given year.
     *
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return District[] containing a District for each active district in the specified year
     */
    public Result<List<District>> getDistricts(int year) {
        return getDistricts(year, null);
    }

    /**
     * Mirror of: /districts/{year}
     * <p>
     * Gets a list of districts and their corresponding district key, for the given year.
     *
     * @param year         Competition Year (or Season). Must be 4 digits.
     * @param lastModified
     * @return District[] containing a District for each active district in the specified year
     */
    public Result<List<District>> getDistricts(int year, String lastModified) {
        return map(dr.getDistricts(year, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return the Team[] array that this event includes
     */
    public Result<List<Team>> getEventTeams(String eventKey) {
        return getEventTeams(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/teams
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return the Team[] array that this event includes
     */
    public Result<List<Team>> getEventTeams(String eventKey, String lastModified) {
        return map(er.getEventTeams(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams/simple
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return the STeam[] array that this event includes (simple model)
     */
    public Result<List<STeam>> getSEventTeams(String eventKey) {
        return getSEventTeams(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/teams/simple
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return the STeam[] array that this event includes (simple model)
     */
    public Result<List<STeam>> getSEventTeams(String eventKey, String lastModified) {
        return map(er.getSEventTeams(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams/keys
     * <p>
     * Gets a list of Team keys that competed in the given event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return String[] containing all the team keys in this event
     */
    public Result<List<String>> getTeamKeys(String eventKey) {
        return getTeamKeys(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/teams/keys
     * <p>
     * Gets a list of Team keys that competed in the given event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return String[] containing all the team keys in this event
     */
    public Result<List<String>> getTeamKeys(String eventKey, String lastModified) {
        return map(er.getTeamKeys(eventKey, lastModified));
    }

    /**
     * Mirror of: /events/{year}
     * <p>
     * Gets a list of events in the given year.
     *
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return Event[] containing all the events in the specified year
     */
    public Result<List<Event>> getEvents(int year) {
        return getEvents(year, null);
    }

    /**
     * Mirror of: /events/{year}
     * <p>
     * Gets a list of events in the given year.
     *
     * @param year         Competition Year (or Season). Must be 4 digits.
     * @param lastModified
     * @return Event[] containing all the events in the specified year
     */
    public Result<List<Event>> getEvents(int year, String lastModified) {
        return map(er.getEvents(year, lastModified));
    }

    /**
     * Mirror of: /events/{year}/simple
     * <p>
     * Gets a list of events in the given year.
     *
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return SEvent[] containing all the events in the specified year
     */
    public Result<List<SEvent>> getSEvents(int year) {
        return getSEvents(year, null);
    }

    /**
     * Mirror of: /events/{year}/simple
     * <p>
     * Gets a list of events in the given year.
     *
     * @param year         Competition Year (or Season). Must be 4 digits.
     * @param lastModified
     * @return SEvent[] containing all the events in the specified year
     */
    public Result<List<SEvent>> getSEvents(int year, String lastModified) {
        return map(er.getSEvents(year, lastModified));
    }

    /**
     * Mirror of: /events/{year}/keys
     * <p>
     * Gets a list of event keys in the given year.
     *
     * @param year Competition Year (or Season). Must be 4 digits.
     * @return String[] containing event keys for the specified year
     */
    public Result<List<String>> getEventKeys(int year) {
        return getEventKeys(year, null);
    }

    /**
     * Mirror of: /events/{year}/keys
     * <p>
     * Gets a list of event keys in the given year.
     *
     * @param year Competition Year (or Season). Must be 4 digits.
     * @param lastModified
     * @return String[] containing event keys for the specified year
     */
    public Result<List<String>> getEventKeys(int year, String lastModified) {
        return map(er.getEventKeys(year, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}
     * <p>
     * Gets an Event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Event model representing the event associated with the event key
     */
    public Result<Event> getEvent(String eventKey) {
        return getEvent(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}
     * <p>
     * Gets an Event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Event model representing the event associated with the event key
     */
    public Result<Event> getEvent(String eventKey, String lastModified) {
        return map(er.getEvent(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/simple
     * <p>
     * Gets an Event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Event model representing the event associated with the event key
     */
    public Result<SEvent> getSEvent(String eventKey) {
        return getSEvent(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/simple
     * <p>
     * Gets an Event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Event model representing the event associated with the event key
     */
    public Result<SEvent> getSEvent(String eventKey, String lastModified) {
        return map(er.getSEvent(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/oprs
     * <p>
     * Gets a set of Event OPRs (including OPR, DPR, and CCWM) for the given Event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return EventOPR[] containing an EventOPR for each team
     */
    public Result<List<EventOPR>> getOprs(String eventKey) {
        return getOprs(eventKey, null);
    }

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
        return er.getPredictions(eventKey);
    }*/

    /**
     * Mirror of: /event/{event_key}/oprs
     * <p>
     * Gets a set of Event OPRs (including OPR, DPR, and CCWM) for the given Event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return EventOPR[] containing an EventOPR for each team
     */
    public Result<List<EventOPR>> getOprs(String eventKey, String lastModified) {
        return map(er.getOprs(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches
     * <p>
     * Gets a list of matches for the given event.
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<Match>> getMatches(String eventKey) {
        return getMatches(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/matches
     *
     * Gets a list of matches for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<Match>> getMatches(String eventKey, String lastModified) {
        return map(er.getMatches(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches/simple
     *
     * Gets a list of matches for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<SMatch>> getSMatches(String eventKey) {
        return getSMatches(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/matches/simple
     * <p>
     * Gets a list of matches for the given event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<SMatch>> getSMatches(String eventKey, String lastModified) {
        return map(er.getSMatches(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches/keys
     *
     * GGets a list of match keys for the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return String[] containing matches keys for the specified event
     */
    public Result<List<String>> getMatchKeys(String eventKey) {
        return getMatchKeys(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/matches/keys
     * <p>
     * GGets a list of match keys for the given event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return String[] containing matches keys for the specified event
     */
    public Result<List<String>> getMatchKeys(String eventKey, String lastModified) {
        return map(er.getMatchKeys(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/awards
     *
     * Gets a list of awards from the given event.
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Award[] containing all the awards won in this event
     */
    public Result<List<Award>> getEventAwards(String eventKey) {
        return getEventAwards(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/awards
     * <p>
     * Gets a list of awards from the given event.
     *
     * @param eventKey     TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Award[] containing all the awards won in this event
     */
    public Result<List<Award>> getEventAwards(String eventKey, String lastModified) {
        return map(er.getEventAwards(eventKey, lastModified));
    }

    /**
     * Mirror of: /match/{match_key}
     *
     * Gets a Match object for the given match key.
     * @param matchKey TBA Match Key, eg 2016nytr_qm1
     * @return Match object represented by the match key
     */
    public Result<Match> getMatch(String matchKey) {
        return getMatch(matchKey, null);
    }

    /**
     * Mirror of: /match/{match_key}
     * <p>
     * Gets a Match object for the given match key.
     *
     * @param matchKey     TBA Match Key, eg 2016nytr_qm1
     * @param lastModified
     * @return Match object represented by the match key
     */
    public Result<Match> getMatch(String matchKey, String lastModified) {
        return map(mr.getMatch(matchKey, lastModified));
    }

    /**
     * Mirror of: /match/{match_key}/simple
     *
     * Gets a Match object for the given match key.
     * @param matchKey TBA Match Key, eg 2016nytr_qm1
     * @return SMatch object represented by the match key (simple model)
     */
    public Result<SMatch> getSMatch(String matchKey) {
        return getSMatch(matchKey, null);
    }

    /**
     * Mirror of: /match/{match_key}/simple
     *
     * Gets a Match object for the given match key.
     * @param matchKey TBA Match Key, eg 2016nytr_qm1
     * @param lastModified
     * @return SMatch object represented by the match key (simple model)
     */
    public Result<SMatch> getSMatch(String matchKey, String lastModified) {
        return map(mr.getSMatch(matchKey, lastModified));
    }

    /**
     * Returns API status, and TBA status information.
     * @return APIStatus representing the state of the TBA API interface
     */
    public Result<APIStatus> getStatus() {
        return map(or.getStatus());
    }

    /**
     * Makes a custom call to the URL
     * @param URL the URL suffix to make a call to, this API automatically fills in Constants.URL for you, so an example parameter here might be 'teams/{page_num}'
     * @return an Object (json formatted), representing the data received from the server
     */
    public Result<Object> customCall(String URL) {
        return map(or.customCall(URL));
    }

    /**
     * Mirror of: /teams/{page_num}
     *
     * Gets a list of Team objects, paginated in groups of 500.
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @return list of Team objects (full team models)
     */
    public Result<List<Team>> getTeams(int pageNum) {
        return getTeams(pageNum, null);
    }

    /**
     * Mirror of: /teams/{page_num}
     *
     * Gets a list of Team objects, paginated in groups of 500.
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @param lastModified
     * @return list of Team objects (full team models)
     */
    public Result<List<Team>> getTeams(int pageNum, String lastModified) {
        return map(tr.getTeams(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{page_num}/simple
     *
     * Gets a list of STeam objects, paginated in groups of 500.
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @return list of STeam objects (simple team models)
     */
    public Result<List<STeam>> getSTeams(int pageNum) {
        return getSTeams(pageNum, null);
    }

    /**
     * Mirror of: /teams/{page_num}/simple
     * <p>
     * Gets a list of STeam objects, paginated in groups of 500.
     *
     * @param pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @param lastModified
     * @return list of STeam objects (simple team models)
     */
    public Result<List<STeam>> getSTeams(int pageNum, String lastModified) {
        return map(tr.getSTeams(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{page_num}/keys
     *
     * Gets a list of Team keys, paginated in groups of 500. (Note, each page will not have 500 teams, but will include the teams within that range of 500.)
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @return String[] of team keys in the format 'frc254'
     */
    public Result<List<String>> getTeamKeys(int pageNum) {
        return getTeamKeys(pageNum, null);
    }

    /**
     * Mirror of: /teams/{page_num}/keys
     *
     * Gets a list of Team keys, paginated in groups of 500. (Note, each page will not have 500 teams, but will include the teams within that range of 500.)
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @param lastModified
     * @return String[] of team keys in the format 'frc254'
     */
    public Result<List<String>> getTeamKeys(int pageNum, String lastModified) {
        return map(tr.getTeamKeys(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{year}/{page_num}
     *
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     * @param year the year to get teams from
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @return list of Team objects (full models)
     */
    public Result<List<Team>> getTeams(int year, int pageNum) {
        return getTeams(year, pageNum, null);
    }

    /**
     * Mirror of: /teams/{year}/{page_num}
     *
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     * @param year the year to get teams from
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @param lastModified
     * @return list of Team objects (full models)
     */
    public Result<List<Team>> getTeams(int year, int pageNum, String lastModified) {
        return map(tr.getTeams(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{year}/{page_num}/simple
     *
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     * @param year the year to get teams from
     * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @return list of Team objects (simple models)
     */
    public Result<List<STeam>> getSTeams(int year, int pageNum) {
        return getSTeams(year, pageNum, null);
    }

    /**
     * Mirror of: /teams/{year}/{page_num}/simple
     * <p>
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     *
     * @param year         the year to get teams from
     * @param pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     * @param lastModified
     * @return list of Team objects (simple models)
     */
    public Result<List<STeam>> getSTeams(int year, int pageNum, String lastModified) {
        return map(tr.getSTeams(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /team/{year}/{page_num}/keys
     *
     * Gets a list Team Keys that competed in the given year, paginated in groups of 500.
     * @param year the year to get teams from
     * @return String[] of team keys in format 'frc254'
     */
    public Result<List<String>> getTeamKeys(int year, int pageNum) {
        return getTeamKeys(year, pageNum, null);
    }

    /**
     * Mirror of: /team/{year}/{page_num}/keys
     *
     * Gets a list Team Keys that competed in the given year, paginated in groups of 500.
     * @param year the year to get teams from
     * @param lastModified
     * @return String[] of team keys in format 'frc254'
     */
    public Result<List<String>> getTeamKeys(int year, int pageNum, String lastModified) {
        return map(tr.getTeamKeys(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}
     *
     * Gets the specified team (full team model)
     * @param number the team's frc number
     * @return Team object (full model)
     */
    public Result<Team> getTeam(int number) {
        return getTeam(number, null);
    }

    /**
     * Mirror of: /team/{team_key}
     *
     * Gets the specified team (full team model)
     * @param number the team's frc number
     * @param lastModified
     * @return Team object (full model)
     */
    public Result<Team> getTeam(int number, String lastModified) {
        return map(tr.getTeam(number, lastModified));
    }

    /**
     * Mirror of: /team{team_key}/simple
     *
     * Gets the specified team (simple team model)
     * @param number the team's frc number
     * @return STeam object (simple model)
     */
    public Result<STeam> getSTeam(int number) {
        return getSTeam(number, null);
    }

    /**
     * Mirror of: /team{team_key}/simple
     *
     * Gets the specified team (simple team model)
     * @param number the team's frc number
     * @param lastModified
     * @return STeam object (simple model)
     */
    public Result<STeam> getSTeam(int number, String lastModified) {
        return map(tr.getSTeam(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/years_participated
     *
     * Returns an array containing the years that a particular team participated in FRC events
     * @param number the team's frc number
     * @return long[] containing years participated
     */
    public Result<List<Long>> getYearsParticipated(int number) {
        return getYearsParticipated(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/years_participated
     *
     * Returns an array containing the years that a particular team participated in FRC events
     * @param number the team's frc number
     * @param lastModified
     * @return long[] containing years participated
     */
    public Result<List<Long>> getYearsParticipated(int number, String lastModified) {
        return map(tr.getYearsParticipated(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/districts
     *
     * Gets the districts this team was in, empty array if none
     * @param number the team's frc number
     * @return District[] containing a District object for each district this team was in
     */
    public Result<List<String>> getTeamDistricts(int number) {
        return getTeamDistricts(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/districts
     * <p>
     * Gets the districts this team was in, empty array if none
     *
     * @param number       the team's frc number
     * @param lastModified
     * @return District[] containing a District object for each district this team was in
     */
    public Result<List<String>> getTeamDistricts(int number, String lastModified) {
        return map(tr.getTeamDistricts(number, lastModified));
    }

    /**
     * Mirror of: /team{team_key}/robots
     *
     * Gets the robots that this team has had
     * @param number the team's frc number
     * @return Robot[] containing a Robot object for each robot this team has built
     *
     *
     */
    public Result<List<Robot>> getRobots(int number) {
        return getRobots(number, null);
    }

    /**
     * Mirror of: /team{team_key}/robots
     *
     * Gets the robots that this team has had
     * @param number the team's frc number
     * @param lastModified
     * @return Robot[] containing a Robot object for each robot this team has built
     *
     *
     */
    public Result<List<Robot>> getRobots(int number, String lastModified) {
        return map(tr.getRobots(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events
     *
     * Gets a list of all events this team has competed at.
     * @param number the team's frc number
     * @return Event[] containing an Event object for each event this team was in
     */
    public Result<List<Event>> getTeamEvents(int number) {
        return getTeamEvents(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/events
     *
     * Gets a list of all events this team has competed at.
     * @param number the team's frc number
     * @param lastModified
     * @return Event[] containing an Event object for each event this team was in
     */
    public Result<List<Event>> getTeamEvents(int number, String lastModified) {
        return map(tr.getTeamEvents(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/simple
     *
     * Gets a list of all events this team has competed at.
     * @param number the team's frc number
     * @return SEvent[] containing an Event object for each event this team was in (simple model)
     */
    public Result<List<SEvent>> getTeamSEvents(int number) {
        return getTeamSEvents(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/events/simple
     *
     * Gets a list of all events this team has competed at.
     * @param number the team's frc number
     * @param lastModified
     * @return SEvent[] containing an Event object for each event this team was in (simple model)
     */
    public Result<List<SEvent>> getTeamSEvents(int number, String lastModified) {
        return map(tr.getTeamSEvents(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events_keys
     *
     * Gets a list of the event keys for all events this team has competed at.
     * @param number the team's frc number
     * @return String[] containg all the event keys for events this team is in
     */
    public Result<List<String>> getTeamEventKeys(int number) {
        return getTeamEventKeys(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/events_keys
     *
     * Gets a list of the event keys for all events this team has competed at.
     * @param number the team's frc number
     * @param lastModified
     * @return String[] containg all the event keys for events this team is in
     */
    public Result<List<String>> getTeamEventKeys(int number, String lastModified) {
        return map(tr.getTeamEventKeys(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}
     *
     * Gets a list of events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @return Event[] containing an Event object for each event this team was in the specified year (full model)
     */
    public Result<List<Event>> getEvents(int number, int year) {
        return getEvents(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}
     *
     * Gets a list of events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @param lastModified
     * @return Event[] containing an Event object for each event this team was in the specified year (full model)
     */
    public Result<List<Event>> getEvents(int number, int year, String lastModified) {
        return map(tr.getEvents(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/simple
     *
     * Gets a short-form list of events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @return Event[] containing an Event object for each event this team was in the specified year (simple model)
     */
    public Result<List<SEvent>> getSEvents(int number, int year) {
        return getSEvents(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/simple
     *
     * Gets a short-form list of events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @param lastModified
     * @return Event[] containing an Event object for each event this team was in the specified year (simple model)
     */
    public Result<List<SEvent>> getSEvents(int number, int year, String lastModified) {
        return map(tr.getSEvents(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/keys
     *
     * Gets a list of the event keys for events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getEventKeys(int number, int year) {
        return getEventKeys(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/keys
     *
     * Gets a list of the event keys for events this team has competed at in the given year.
     * @param number the team's frc number
     * @param year the year to get events from
     * @param lastModified
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getEventKeys(int number, int year, String lastModified) {
        return map(tr.getEventKeys(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches
     *
     * Gets a list of matches for the given team and event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @return Match[] containing a match for each match this team was in in the specified event
     */
    public Result<List<Match>> getTeamEventMatches(int number, String eventKey) {
        return getTeamEventMatches(number, eventKey, null);
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches
     *
     * Gets a list of matches for the given team and event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @param lastModified
     * @return Match[] containing a match for each match this team was in in the specified event
     */
    public Result<List<Match>> getTeamEventMatches(int number, String eventKey, String lastModified) {
        return map(tr.getTeamEventMatches(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/rankings
     * @param eventKey the event's key code (example: '2016nytr')
     * @return EventRanking[] containing rankings of teams in this event
     */
    public Result<List<EventRanking>> getEventRankings(String eventKey) {
        return getEventRankings(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/rankings
     * @param eventKey the event's key code (example: '2016nytr')
     * @param lastModified
     * @return EventRanking[] containing rankings of teams in this event
     */
    public Result<List<EventRanking>> getEventRankings(String eventKey, String lastModified) {
        return map(er.getEventRankings(eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/simple
     *
     * Gets a short-form list of matches for the given team and event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @return SMatch[] containing a match for each match this team was in in the specified event (simple model)
     */
    public Result<List<SMatch>> getTeamEventSMatches(int number, String eventKey) {
        return getTeamEventSMatches(number, eventKey, null);
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/simple
     *
     * Gets a short-form list of matches for the given team and event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @param lastModified
     * @return SMatch[] containing a match for each match this team was in in the specified event (simple model)
     */
    public Result<List<SMatch>> getTeamEventSMatches(int number, String eventKey, String lastModified) {
        return map(tr.getTeamEventSMatches(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/keys
     *
     * Gets a list of the event keys for events this team has competed at in the given year.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getMatchKeys(int number, String eventKey) {
        return getMatchKeys(number, eventKey, null);
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/keys
     *
     * Gets a list of the event keys for events this team has competed at in the given year.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @param lastModified
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getMatchKeys(int number, String eventKey, String lastModified) {
        return map(tr.getMatchKeys(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/awards
     *
     * Gets a list of awards the given team won at the given event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @return Award[] containing n award object for each award this team won in the specified event
     */
    public Result<List<Award>> getTeamEventAwards(int number, String eventKey) {
        return getTeamEventAwards(number, eventKey, null);
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/awards
     *
     * Gets a list of awards the given team won at the given event.
     * @param number the team's frc number
     * @param eventKey the event's key code (example: '2016nytr')
     * @param lastModified
     * @return Award[] containing n award object for each award this team won in the specified event
     */
    public Result<List<Award>> getTeamEventAwards(int number, String eventKey, String lastModified) {
        return map(tr.getTeamEventAwards(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/awards
     *
     * Gets a list of awards the given team has won.
     * @param number the team's frc number
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwards(int number) {
        return getTeamAwards(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/awards
     *
     * Gets a list of awards the given team has won.
     * @param number the team's frc number
     * @param lastModified
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwards(int number, String lastModified) {
        return map(tr.getTeamAwards(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/awards/{year}
     *
     * Gets a list of awards the given team has won.
     * @param number the team's frc number
     * @param year the year
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwards(int number, int year) {
        return getTeamAwards(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/awards/{year}
     *
     * Gets a list of awards the given team has won.
     * @param number the team's frc number
     * @param year the year
     * @param lastModified
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwards(int number, int year, String lastModified) {
        return map(tr.getTeamAwards(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}
     *
     * Gets a list of matches for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @return Match[] containing all the matches the specified team was in for the specified year
     */
    public Result<List<Match>> getTeamMatches(int number, int year) {
        return getTeamMatches(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}
     *
     * Gets a list of matches for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @param lastModified
     * @return Match[] containing all the matches the specified team was in for the specified year
     */
    public Result<List<Match>> getTeamMatches(int number, int year, String lastModified) {
        return map(tr.getTeamMatches(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/simple
     *
     * Gets a list of matches for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @return SMatch[] containing all the matches the specified team was in for the specified year (simple models)
     */
    public Result<List<SMatch>> getTeamSMatches(int number, int year) {
        return getTeamSMatches(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/simple
     *
     * Gets a list of matches for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @param lastModified
     * @return SMatch[] containing all the matches the specified team was in for the specified year (simple models)
     */
    public Result<List<SMatch>> getTeamSMatches(int number, int year, String lastModified) {
        return map(tr.getTeamSMatches(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/keys
     *
     * Gets a list of match keys for matches for the given team and year.
     * @param number the team's frc number
     * @param year the year to get match keys from
     * @return String[] containing match string keys for each match
     */
    public Result<List<String>> getTeamMatchKeys(int number, int year) {
        return getTeamMatchKeys(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/keys
     *
     * Gets a list of match keys for matches for the given team and year.
     * @param number the team's frc number
     * @param year the year to get match keys from
     * @param lastModified
     * @return String[] containing match string keys for each match
     */
    public Result<List<String>> getTeamMatchKeys(int number, int year, String lastModified) {
        return map(tr.getTeamMatchKeys(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/media/{year}
     *
     * Gets a list of Media (videos / pictures) for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @return Media[] containing all the media associated with this team for the specified year
     */
    public Result<List<Media>> getTeamMedia(int number, int year) {
        return getTeamMedia(number, year, null);
    }

    /**
     * Mirror of: /team/{team_key}/media/{year}
     *
     * Gets a list of Media (videos / pictures) for the given team and year.
     * @param number the team's frc number
     * @param year the year
     * @param lastModified
     * @return Media[] containing all the media associated with this team for the specified year
     */
    public Result<List<Media>> getTeamMedia(int number, int year, String lastModified) {
        return map(tr.getTeamMedia(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/social_media
     *
     * Gets a list of Media (social media) for the given team.
     * @param number the team's frc number
     * @return Media[] containing all social media associated with this team
     */
    public Result<List<Media>> getTeamSocialMedia(int number) {
        return getTeamSocialMedia(number, null);
    }

    /**
     * Mirror of: /team/{team_key}/social_media
     *
     * Gets a list of Media (social media) for the given team.
     * @param number the team's frc number
     * @param lastModified
     * @return Media[] containing all social media associated with this team
     */
    public Result<List<Media>> getTeamSocialMedia(int number, String lastModified) {
        return map(tr.getTeamSocialMedia(number, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/alliances
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return List of all alliances in this event
     */
    public Result<List<Alliance>> getEventAlliances(String eventKey) {
        return getEventAlliances(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/alliances
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return List of all alliances in this event
     */
    public Result<List<Alliance>> getEventAlliances(String eventKey, String lastModified) {
        return map(er.getEventAlliances(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/insights
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @return Insights for this event
     */
    public Result<Insight> getEventInsights(String eventKey) {
        return getEventInsights(eventKey, null);
    }

    /**
     * Mirror of: /event/{event_key}/insights
     *
     * @param eventKey TBA Event Key, eg 2016nytr
     * @param lastModified
     * @return Insights for this event
     */
    public Result<Insight> getEventInsights(String eventKey, String lastModified) {
        return map(er.getEventInsights(eventKey, lastModified));
    }
}