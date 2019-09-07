package com.npmanos.requests;

import com.npmanos.models.APIStatus;

import retrofit2.Response;
import retrofit2.http.GET;

/**
 * @since 1.0.0
 * @author Will Davies
 */
public interface OtherRequest {

    /**
     * Returns API status, and TBA status information.
     * @return Response<APIStatus> representing the state of the TBA API interface
     */
    @GET("status")
    Response<APIStatus> getStatus();

    /**
     * Makes a custom call to the URL
     * @param Url the URL suffix to make a call to, this API automatically fills in Constants.URL for you, so an example parameter here might be 'teams/{page_num}'
     * @return an Object (json formatted), representing the data received from the server
     */
    @GET("{Url}")
    Response<Object> customCall(String Url);

}