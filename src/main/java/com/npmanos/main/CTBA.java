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
import com.npmanos.utils.Result;
import com.npmanos.utils.RetrofitIO;

import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This is an alternative to the TBA class. It allows you to use constructors/getters/setters. For more information on this,
 * read the javadoc header for the TBA class.
 *
 * @since 1.0.0
 * @author Will Davies
 */
@SuppressWarnings("unused")
public class CTBA {

    /**
     * TBA District Key, eg 2016fim
     */
    private String districtKey;
    /**
     * TBA Team Key, eg frc254
     */
    private int number;
    /**
     * Page number of results to return, zero-indexed
     */
    private int pageNum;
    /**
     * TBA Event Key, eg 2016nytr
     */
    private String eventKey;
    /**
     * TBA Match Key, eg 2016nytr_qm1
     */
    private String matchKey;
    /**
     * Competition Year (or Season). Must be 4 digits.
     */
    private int year;
    /**
     * Last modified header
     */
    private String lastModified;

    private DistrictRequest dr;
    private EventRequest er;
    private MatchRequest mr;
    private OtherRequest or;
    private TeamRequest tr;

    public CTBA() {
        Retrofit retrofitConfig = RetrofitIO.getRetrofitConfig();
        dr = retrofitConfig.create(DistrictRequest.class);
        er = retrofitConfig.create(EventRequest.class);
        mr = retrofitConfig.create(MatchRequest.class);
        or = retrofitConfig.create(OtherRequest.class);
        tr = retrofitConfig.create(TeamRequest.class);
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
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return Team[] including a Team object for every team in the specified district
     */
    public Result<List<Team>> getDistrictTeams() {
        return map(dr.getDistrictTeams(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/teams/simple
     *
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return STeam[] including a STeam object for every team in the specified district (simple model)
     */
    public Result<List<STeam>> getDistrictSTeams() {
        return map(dr.getDistrictSTeams(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/teams/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictTeamKeys() {
        return map(dr.getDistrictTeamKeys(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events
     *
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return Event[] including an Event object for every event in the specified district
     */
    public Result<List<Event>> getDistrictEvents() {
        return map(dr.getDistrictEvents(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events/simple
     *
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return SEvent[] including an SEvent object for every event in the specified district (simple model)
     */
    public Result<List<SEvent>> getDistrictSEvents() {
        return map(dr.getDistrictSEvents(districtKey, lastModified));
    }

    /**
     * Mirror of: /district/{district_key}/events/keys
     * <p>
     * Gets a list of Team objects that competed in events in the given district.
     *
     *  districtKey  TBA District Key, eg 2016fim
     *  lastModified
     * @return String[] containing all the team keys in this district
     */
    public Result<List<String>> getDistrictEventKeys() {
        return map(dr.getDistrictEventKeys(districtKey, lastModified));
    }

    /**
     * Mirror of: /districts/{year}
     * <p>
     * Gets a list of districts and their corresponding district key, for the given year.
     *
     *  year         Competition Year (or Season). Must be 4 digits.
     *  lastModified
     * @return District[] containing a District for each active district in the specified year
     */
    public Result<List<District>> getDistricts() {
        return map(dr.getDistricts(year, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return the Team[] array that this event includes
     */
    public Result<List<Team>> getEventTeams() {
        return map(er.getEventTeams(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams/simple
     * <p>
     * Gets a list of Team objects that competed in the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return the STeam[] array that this event includes (simple model)
     */
    public Result<List<STeam>> getSEventTeams() {
        return map(er.getSEventTeams(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/teams/keys
     * <p>
     * Gets a list of Team keys that competed in the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return String[] containing all the team keys in this event
     */
    public Result<List<String>> getTeamKeys() {
        return map(er.getTeamKeys(eventKey, lastModified));
    }

    /**
     * Mirror of: /events/{year}
     * <p>
     * Gets a list of events in the given year.
     *
     *  year         Competition Year (or Season). Must be 4 digits.
     *  lastModified
     * @return Event[] containing all the events in the specified year
     */
    public Result<List<Event>> getEvents() {
        return map(er.getEvents(year, lastModified));
    }

    /**
     * Mirror of: /events/{year}/simple
     * <p>
     * Gets a list of events in the given year.
     *
     *  year         Competition Year (or Season). Must be 4 digits.
     *  lastModified
     * @return SEvent[] containing all the events in the specified year
     */
    public Result<List<SEvent>> getSEvents() {
        return map(er.getSEvents(year, lastModified));
    }

    /**
     * Mirror of: /events/{year}/keys
     * <p>
     * Gets a list of event keys in the given year.
     *
     *  year         Competition Year (or Season). Must be 4 digits.
     *  lastModified
     * @return String[] containing event keys for the specified year
     */
    public Result<List<String>> getEventKeys() {
        return map(er.getEventKeys(year, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}
     * <p>
     * Gets an Event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return Event model representing the event associated with the event key
     */
    public Result<Event> getEvent() {
        return map(er.getEvent(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/simple
     * <p>
     * Gets an Event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return Event model representing the event associated with the event key
     */
    public Result<SEvent> getSEvent() {
        return map(er.getSEvent(eventKey, lastModified));
    }

    /*
     * Mirror of: /event/{event_key}/predictions
     *
     * Gets information on TBA-generated predictions for the given Event. Contains year-specific information. WARNING This endpoint is currently under development and may change at any time.
     *
     * Not stable! No official model for this yet.
     *  eventKey TBA Event Key, eg 2016nytr
     * @return JSON String containing prediction information
     */
    /*public String getPredictions() {
        return er.getPredictions(eventKey);
    }*/

    /**
     * Mirror of: /event/{event_key}/oprs
     * <p>
     * Gets a set of Event OPRs (including OPR, DPR, and CCWM) for the given Event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return EventOPR[] containing an EventOPR for each team
     */
    public Result<List<EventOPR>> getOprs() {
        return map(er.getOprs(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches
     * <p>
     * Gets a list of matches for the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<Match>> getMatches() {
        return map(er.getMatches(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches/simple
     * <p>
     * Gets a list of matches for the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return Match[] containing a Match object for each match in the specified event
     */
    public Result<List<SMatch>> getSMatches() {
        return map(er.getSMatches(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/matches/keys
     * <p>
     * GGets a list of match keys for the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return String[] containing matches keys for the specified event
     */
    public Result<List<String>> getMatchKeys() {
        return map(er.getMatchKeys(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/awards
     * <p>
     * Gets a list of awards from the given event.
     *
     *  eventKey     TBA Event Key, eg 2016nytr
     *  lastModified
     * @return Award[] containing all the awards won in this event
     */
    public Result<List<Award>> getEventAwards() {
        return map(er.getEventAwards(eventKey, lastModified));
    }

    /**
     * Mirror of: /match/{match_key}
     * <p>
     * Gets a Match object for the given match key.
     *
     *  matchKey     TBA Match Key, eg 2016nytr_qm1
     *  lastModified
     * @return Match object represented by the match key
     */
    public Result<Match> getMatch() {
        return map(mr.getMatch(matchKey, lastModified));
    }

    /**
     * Mirror of: /match/{match_key}/simple
     * <p>
     * Gets a Match object for the given match key.
     *
     *  matchKey     TBA Match Key, eg 2016nytr_qm1
     *  lastModified
     * @return SMatch object represented by the match key (simple model)
     */
    public Result<SMatch> getSMatch() {
        return map(mr.getSMatch(matchKey, lastModified));
    }

    /**
     * Returns API status, and TBA status information.
     *
     * @return APIStatus representing the state of the TBA API interface
     */
    public Result<APIStatus> getStatus() {
        return map(or.getStatus());
    }

    /**
     * Makes a custom call to the URL
     *
     *  URL the URL suffix to make a call to, this API automatically fills in Constants.URL for you, so an example parameter here might be 'teams/{page_num}'
     * @return an Object (json formatted), representing the data received from the server
     */
    public Result<Object> customCall(String URL) {
        return map(or.customCall(URL));
    }

    /**
     * Mirror of: /teams/{page_num}
     * <p>
     * Gets a list of Team objects, paginated in groups of 500.
     *
     *  pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     *  lastModified
     * @return list of Team objects (full team models)
     */
    public Result<List<Team>> getTeams() {
        return map(tr.getTeams(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{page_num}/simple
     * <p>
     * Gets a list of STeam objects, paginated in groups of 500.
     *
     *  pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     *  lastModified
     * @return list of STeam objects (simple team models)
     */
    public Result<List<STeam>> getSTeams() {
        return map(tr.getSTeams(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{page_num}/keys
     * <p>
     * Gets a list of Team keys, paginated in groups of 500. (Note, each page will not have 500 teams, but will include the teams within that range of 500.)
     *
     *  pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     *  lastModified
     * @return String[] of team keys in the format 'frc254'
     */
    public Result<List<String>> getTeamKeysByPage() {
        return map(tr.getTeamKeys(pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{year}/{page_num}
     * <p>
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     *
     *  year         the year to get teams from
     *  pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     *  lastModified
     * @return list of Team objects (full models)
     */
    public Result<List<Team>> getYearTeamsByPage() {
        return map(tr.getTeams(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /teams/{year}/{page_num}/simple
     * <p>
     * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
     *
     *  year         the year to get teams from
     *  pageNum      the page number, eg: 0 for the first 500, 1 for the second 500, etc.
     *  lastModified
     * @return list of Team objects (simple models)
     */
    public Result<List<STeam>> getYearSTeamsByPage() {
        return map(tr.getSTeams(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /team/{year}/{page_num}/keys
     * <p>
     * Gets a list Team Keys that competed in the given year, paginated in groups of 500.
     *
     *  year         the year to get teams from
     *  lastModified
     * @return String[] of team keys in format 'frc254'
     */
    public Result<List<String>> getYearTeamKeysByPage() {
        return map(tr.getTeamKeys(year, pageNum, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}
     * <p>
     * Gets the specified team (full team model)
     *
     *  number       the team's frc number
     *  lastModified
     * @return Team object (full model)
     */
    public Result<Team> getTeam() {
        return map(tr.getTeam(number, lastModified));
    }

    /**
     * Mirror of: /team{team_key}/simple
     * <p>
     * Gets the specified team (simple team model)
     *
     *  number       the team's frc number
     *  lastModified
     * @return STeam object (simple model)
     */
    public Result<STeam> getSTeam() {
        return map(tr.getSTeam(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/years_participated
     * <p>
     * Returns an array containing the years that a particular team participated in FRC events
     *
     *  number       the team's frc number
     *  lastModified
     * @return long[] containing years participated
     */
    public Result<List<Long>> getYearsParticipated() {
        return map(tr.getYearsParticipated(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/districts
     * <p>
     * Gets the districts this team was in, empty array if none
     *
     *  number       the team's frc number
     *  lastModified
     * @return District[] containing a District object for each district this team was in
     */
    public Result<List<String>> getTeamDistricts() {
        return map(tr.getTeamDistricts(number, lastModified));
    }

    /**
     * Mirror of: /team{team_key}/robots
     * <p>
     * Gets the robots that this team has had
     *
     *  number       the team's frc number
     *  lastModified
     * @return Robot[] containing a Robot object for each robot this team has built
     */
    public Result<List<Robot>> getRobots() {
        return map(tr.getRobots(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events
     * <p>
     * Gets a list of all events this team has competed at.
     *
     *  number       the team's frc number
     *  lastModified
     * @return Event[] containing an Event object for each event this team was in
     */
    public Result<List<Event>> getTeamEvents() {
        return map(tr.getTeamEvents(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/simple
     * <p>
     * Gets a list of all events this team has competed at.
     *
     *  number       the team's frc number
     *  lastModified
     * @return SEvent[] containing an Event object for each event this team was in (simple model)
     */
    public Result<List<SEvent>> getTeamSEvents() {
        return map(tr.getTeamSEvents(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events_keys
     * <p>
     * Gets a list of the event keys for all events this team has competed at.
     *
     *  number       the team's frc number
     *  lastModified
     * @return String[] containg all the event keys for events this team is in
     */
    public Result<List<String>> getTeamEventKeys() {
        return map(tr.getTeamEventKeys(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}
     * <p>
     * Gets a list of events this team has competed at in the given year.
     *
     *  number       the team's frc number
     *  year         the year to get events from
     *  lastModified
     * @return Event[] containing an Event object for each event this team was in the specified year (full model)
     */
    public Result<List<Event>> getTeamEventsByYear() {
        return map(tr.getEvents(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/simple
     * <p>
     * Gets a short-form list of events this team has competed at in the given year.
     *
     *  number       the team's frc number
     *  year         the year to get events from
     *  lastModified
     * @return Event[] containing an Event object for each event this team was in the specified year (simple model)
     */
    public Result<List<SEvent>> getTeamSEventsByYear() {
        return map(tr.getSEvents(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/events/{year}/keys
     * <p>
     * Gets a list of the event keys for events this team has competed at in the given year.
     *
     *  number       the team's frc number
     *  year         the year to get events from
     *  lastModified
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getTeamEventKeysByYear() {
        return map(tr.getEventKeys(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches
     * <p>
     * Gets a list of matches for the given team and event.
     *
     *  number       the team's frc number
     *  eventKey     the event's key code (example: '2016nytr')
     *  lastModified
     * @return Match[] containing a match for each match this team was in in the specified event
     */
    public Result<List<Match>> getTeamEventMatches() {
        return map(tr.getTeamEventMatches(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/rankings
     * <p>
     * eventKey     the event's key code (example: '2016nytr')
     * lastModified
     *
     * @return EventRanking[] containing rankings of teams in this event
     */
    public Result<List<EventRanking>> getEventRankings() {
        return map(er.getEventRankings(eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/simple
     * <p>
     * Gets a short-form list of matches for the given team and event.
     *
     *  number       the team's frc number
     *  eventKey     the event's key code (example: '2016nytr')
     *  lastModified
     * @return SMatch[] containing a match for each match this team was in in the specified event (simple model)
     */
    public Result<List<SMatch>> getTeamEventSMatches() {
        return map(tr.getTeamEventSMatches(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/matches/keys
     * <p>
     * Gets a list of the event keys for events this team has competed at in the given year.
     *
     *  number       the team's frc number
     *  eventKey     the event's key code (example: '2016nytr')
     *  lastModified
     * @return String[] containing an event key for each event this team has participated in
     */
    public Result<List<String>> getTeamEventMatchKeys() {
        return map(tr.getMatchKeys(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/event/{event_key}/awards
     * <p>
     * Gets a list of awards the given team won at the given event.
     *
     *  number       the team's frc number
     *  eventKey     the event's key code (example: '2016nytr')
     *  lastModified
     * @return Award[] containing n award object for each award this team won in the specified event
     */
    public Result<List<Award>> getTeamEventAwards() {
        return map(tr.getTeamEventAwards(number, eventKey, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/awards
     * <p>
     * Gets a list of awards the given team has won.
     *
     *  number       the team's frc number
     *  lastModified
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwards() {
        return map(tr.getTeamAwards(number, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/awards/{year}
     * <p>
     * Gets a list of awards the given team has won.
     *
     * @return Award[] containing all the awards this team has won
     */
    public Result<List<Award>> getTeamAwardsByYear() {
        return map(tr.getTeamAwards(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}
     * <p>
     * Gets a list of matches for the given team and year.
     *
     * @return Match[] containing all the matches the specified team was in for the specified year
     */
    public Result<List<Match>> getTeamMatches() {
        return map(tr.getTeamMatches(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/simple
     * <p>
     * Gets a list of matches for the given team and year.
     *
     * @return SMatch[] containing all the matches the specified team was in for the specified year (simple models)
     */
    public Result<List<SMatch>> getTeamSMatchesByYear() {
        return map(tr.getTeamSMatches(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/matches/{year}/keys
     * <p>
     * Gets a list of match keys for matches for the given team and year.
     *
     * @return String[] containing match string keys for each match
     */
    public Result<List<String>> getTeamMatchKeysByYear() {
        return map(tr.getTeamMatchKeys(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/media/{year}
     * <p>
     * Gets a list of Media (videos / pictures) for the given team and year.
     *
     * @return Media[] containing all the media associated with this team for the specified year
     */
    public Result<List<Media>> getTeamMedia() {
        return map(tr.getTeamMedia(number, year, lastModified));
    }

    /**
     * Mirror of: /team/{team_key}/social_media
     * <p>
     * Gets a list of Media (social media) for the given team.
     *
     * @return Media[] containing all social media associated with this team
     */
    public Result<List<Media>> getTeamSocialMedia() {
        return map(tr.getTeamSocialMedia(number, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/alliances
     *
     * @return List of all alliances in this event
     */
    public Result<List<Alliance>> getEventAlliances() {
        return map(er.getEventAlliances(eventKey, lastModified));
    }

    /**
     * Mirror of: /event/{event_key}/insights
     *
     * @return Insights for this event
     */
    public Result<Insight> getEventInsights() {
        return map(er.getEventInsights(eventKey, lastModified));
    }

    public void setDistrictKey(String districtKey) {
        this.districtKey = districtKey;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDistrictKey() {
        return districtKey;
    }

    public int getNumber() {
        return number;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getEventKey() {
        return eventKey;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public int getYear() {
        return year;
    }

    public DistrictRequest getDr() {
        return dr;
    }

    public EventRequest getEr() {
        return er;
    }

    public MatchRequest getMr() {
        return mr;
    }

    public OtherRequest getOr() {
        return or;
    }

    public TeamRequest getTr() {
        return tr;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}

    