package com.npmanos.requests;

import com.npmanos.models.events.Award;
import com.npmanos.models.events.Event;
import com.npmanos.models.events.Media;
import com.npmanos.models.events.SEvent;
import com.npmanos.models.matches.Match;
import com.npmanos.models.matches.SMatch;
import com.npmanos.models.teams.Robot;
import com.npmanos.models.teams.STeam;
import com.npmanos.models.teams.Team;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * In an attempt to keep this API organized, if you look at the blue alliance v3 documentation, all calls that start with /teams/ or /team/
 * will be accessed from this class.
 * <p>
 * API calls not implemented yet:
 * /team/{team_key}/event/{event_key}/status
 *
 * @author Will Davies
 * @since 1.0.0
 */
public interface TeamRequest {

	/**
	 * Mirror of: /teams/{page_num}
	 * <p>
	 * Gets a list of Team objects, paginated in groups of 500.
	 *
	 * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
	 * @return list of Team objects (full team models)
	 */
	@GET("teams/{pageNum}")
	Response<List<Team>> getTeams(int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /teams/{page_num}/simple
	 * <p>
	 * Gets a list of STeam objects, paginated in groups of 500.
	 *
	 * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
	 * @return list of STeam objects (simple team models)
	 */
	@GET("teams/{pageNum}/simple")
	Response<List<STeam>> getSTeams(int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /teams/{page_num}/keys
	 * <p>
	 * Gets a list of Team keys, paginated in groups of 500. (Note, each page will not have 500 teams, but will include the teams within that range of 500.)
	 *
	 * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
	 * @return String[] of team keys in the format 'frc254'
	 */
	@GET("teams/{pageNum}/keys")
	Response<List<String>> getTeamKeys(int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /teams/{year}/{page_num}
	 * <p>
	 * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
	 *
	 * @param year    the year to get teams from
	 * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
	 * @return list of Team objects (full models)
	 */
	@GET("teams/{year}/{pageNum}")
	Response<List<Team>> getTeams(int year, int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /teams/{year}/{page_num}/simple
	 * <p>
	 * Gets a list of Team objects that competed in the given year, paginated in groups of 500.
	 *
	 * @param year    the year to get teams from
	 * @param pageNum the page number, eg: 0 for the first 500, 1 for the second 500, etc.
	 * @return list of Team objects (simple models)
	 */
	@GET("teams/{year}/{pageNum}/simple")
	Response<List<STeam>> getSTeams(int year, int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{year}/{page_num}/keys
	 * <p>
	 * Gets a list Team Keys that competed in the given year, paginated in groups of 500.
	 *
	 * @param year the year to get teams from
	 * @return String[] of team keys in format 'frc254'
	 */
	@GET("teams/{year}/{pageNum}/keys")
	Response<List<String>> getTeamKeys(int year, int pageNum, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}
	 * <p>
	 * Gets the specified team (full team model)
	 *
	 * @param number the team's frc number
	 * @return Team object (full model)
	 */
	@GET("teams/frc{number}")
	Response<Team> getTeam(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team{team_key}/simple
	 * <p>
	 * Gets the specified team (simple team model)
	 *
	 * @param number the team's frc number
	 * @return STeam object (simple model)
	 */
	@GET("teams/frc{number}/simple")
	Response<STeam> getSTeam(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/years_participated
	 * <p>
	 * Returns an array containing the years that a particular team participated in FRC events
	 *
	 * @param number the team's frc number
	 * @return long[] containing years participated
	 */
	@GET("teams/frc{number}/years_participated")
	Response<List<Long>> getYearsParticipated(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/districts
	 * <p>
	 * Gets the districts this team was in, empty array if none
	 *
	 * @param number the team's frc number
	 * @return District[] containing a District object for each district this team was in
	 */
	@GET("teams/frc{number}/districts")
	Response<List<String>> getTeamDistricts(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/robots
	 * <p>
	 * Gets the robots that this team has had
	 *
	 * @param number the team's frc number
	 * @return Robot[] containing a Robot object for each robot this team has built
	 */
	@GET("teams/frc{number}/robots")
	Response<List<Robot>> getRobots(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events
	 * <p>
	 * Gets a list of all events this team has competed at.
	 *
	 * @param number the team's frc number
	 * @return Event[] containing an Event object for each event this team was in
	 */
	@GET("teams/frc{number}/events")
	Response<List<Event>> getTeamEvents(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events/simple
	 * <p>
	 * Gets a list of all events this team has competed at.
	 *
	 * @param number the team's frc number
	 * @return SEvent[] containing an Event object for each event this team was in (simple model)
	 */
	@GET("teams/frc{number}/events/simple")
	Response<List<SEvent>> getTeamSEvents(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events_keys
	 * <p>
	 * Gets a list of the event keys for all events this team has competed at.
	 *
	 * @param number the team's frc number
	 * @return String[] containg all the event keys for events this team is in
	 */
	@GET("teams/frc{number}/events/keys")
	Response<List<String>> getTeamEventKeys(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events/{year}
	 * <p>
	 * Gets a list of events this team has competed at in the given year.
	 *
	 * @param number the team's frc number
	 * @param year   the year to get events from
	 * @return Event[] containing an Event object for each event this team was in the specified year (full model)
	 */
	@GET("teams/frc{number}/events/{year}")
	Response<List<Event>> getEvents(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events/{year}/simple
	 * <p>
	 * Gets a short-form list of events this team has competed at in the given year.
	 *
	 * @param number the team's frc number
	 * @param year   the year to get events from
	 * @return Event[] containing an Event object for each event this team was in the specified year (simple model)
	 */
	@GET("teams/frc{number}/events/{year}/simple")
	Response<List<SEvent>> getSEvents(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/events/{year}/keys
	 * <p>
	 * Gets a list of the event keys for events this team has competed at in the given year.
	 *
	 * @param number the team's frc number
	 * @param year   the year to get events from
	 * @return String[] containing an event key for each event this team has participated in
	 */
	@GET("teams/frc{number}/events/{year}/keys")
	Response<List<String>> getEventKeys(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/event/{event_key}/matches
	 * <p>
	 * Gets a list of matches for the given team and event.
	 *
	 * @param number   the team's frc number
	 * @param eventKey the event's key code (example: '2016nytr')
	 * @return Match[] containing a match for each match this team was in in the specified event
	 */
	@GET("teams/frc{number}/event/{eventKey}/matches")
	Response<List<Match>> getTeamEventMatches(int number, String eventKey, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/event/{event_key}/matches/simple
	 * <p>
	 * Gets a short-form list of matches for the given team and event.
	 *
	 * @param number   the team's frc number
	 * @param eventKey the event's key code (example: '2016nytr')
	 * @return SMatch[] containing a match for each match this team was in in the specified event (simple model)
	 */
	@GET("teams/frc{number}/event/{eventKey}/matches/simple")
	Response<List<SMatch>> getTeamEventSMatches(int number, String eventKey, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/event/{event_key}/matches/keys
	 * <p>
	 * Gets a list of the event keys for events this team has competed at in the given year.
	 *
	 * @param number   the team's frc number
	 * @param eventKey the event's key code (example: '2016nytr')
	 * @return String[] containing an event key for each event this team has participated in
	 */
	@GET("teams/frc{number}/event/{eventKey}/matches/keys")
	Response<List<String>> getMatchKeys(int number, String eventKey, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/event/{event_key}/awards
	 * <p>
	 * Gets a list of awards the given team won at the given event.
	 *
	 * @param number   the team's frc number
	 * @param eventKey the event's key code (example: '2016nytr')
	 * @return Award[] containing n award object for each award this team won in the specified event
	 */
	@GET("teams/frc{number}/event/{eventKey}/awards")
	Response<List<Award>> getTeamEventAwards(int number, String eventKey, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/awards
	 * <p>
	 * Gets a list of awards the given team has won.
	 *
	 * @param number the team's frc number
	 * @return Award[] containing all the awards this team has won
	 */
	@GET("teams/frc{number}/awards")
	Response<List<Award>> getTeamAwards(int number, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/awards/{year}
	 * <p>
	 * Gets a list of awards the given team has won.
	 *
	 * @param number the team's frc number
	 * @param year   the year
	 * @return Award[] containing all the awards this team has won
	 */
	@GET("teams/frc{number}/awards/{year}")
	Response<List<Award>> getTeamAwards(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/matches/{year}
	 * <p>
	 * Gets a list of matches for the given team and year.
	 *
	 * @param number the team's frc number
	 * @param year   the year
	 * @return Match[] containing all the matches the specified team was in for the specified year
	 */
	@GET("teams/frc{number}/matches/{year}")
	Response<List<Match>> getTeamMatches(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/matches/{year}/simple
	 * <p>
	 * Gets a list of matches for the given team and year.
	 *
	 * @param number the team's frc number
	 * @param year   the year
	 * @return SMatch[] containing all the matches the specified team was in for the specified year (simple models)
	 */
	@GET("teams/frc{number}/matches/{year}/simple")
	Response<List<SMatch>> getTeamSMatches(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/matches/{year}/keys
	 * <p>
	 * Gets a list of match keys for matches for the given team and year.
	 *
	 * @param number the team's frc number
	 * @param year   the year to get match keys from
	 * @return String[] containing match string keys for each match
	 */
	@GET("teams/frc{number}/matches/{year}/keys")
	Response<List<String>> getTeamMatchKeys(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/media/{year}
	 * <p>
	 * Gets a list of Media (videos / pictures) for the given team and year.
	 *
	 * @param number the team's frc number
	 * @param year   the year
	 * @return Media[] containing all the media associated with this team for the specified year
	 */
	@GET("teams/frc{number}/media/{year}")
	Response<List<Media>> getTeamMedia(int number, int year, @Header("If-Modified-Since") String lastModified);

	/**
	 * Mirror of: /team/{team_key}/social_media
	 * <p>
	 * Gets a list of Media (social media) for the given team.
	 *
	 * @param number the team's frc number
	 * @return Media[] containing all social media associated with this team
	 */
	@GET("teams/frc{number}/social_media")
	Response<List<Media>> getTeamSocialMedia(int number, @Header("If-Modified-Since") String lastModified);
}
