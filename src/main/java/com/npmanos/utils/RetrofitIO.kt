package com.npmanos.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.npmanos.main.Constants
import com.npmanos.utils.exceptions.AuthTokenNotFoundException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitIO {

	private val authInterceptor = object : Interceptor {
		override fun intercept(chain: Interceptor.Chain): Response {
			if (Constants.AUTH_TOKEN == null || Constants.AUTH_TOKEN == "") throw AuthTokenNotFoundException("You have not set an auth token for TBA-API-V3. Please set it with TBA.setAuthToken(String token).")

			var request = chain.request()
			val headers = request.headers().newBuilder()
					.add("User-Agent", "TBA-API-V3")
					.add("X-TBA-Auth-Key", Constants.AUTH_TOKEN)
					.add("Content-SortingType", "application/x-www-form-urlencoded")
					.add("charset", "utf-8")
					.build()

			request = request.newBuilder().headers(headers).build()

			return chain.proceed(request)
		}
	}

	private val client = OkHttpClient.Builder().addInterceptor(authInterceptor).build()

	private val retrofitConfig = Retrofit.Builder()
			.client(client)
			.baseUrl(Constants.URL)
			.addConverterFactory(ScalarsConverterFactory.create())
			.addConverterFactory(GsonConverterFactory.create(GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create()))
			.build()

	@JvmStatic
	fun getRetrofitConfig(): Retrofit = retrofitConfig
}