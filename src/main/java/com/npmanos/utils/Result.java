package com.npmanos.utils;

public abstract class Result<T> {
	private Result() {
	}

	public final static class Success<T> extends Result<T> {
		private final T body;
		private final String lastModified;

		public Success(T body, String lastModified) {
			this.body = body;
			this.lastModified = lastModified;
		}

		public T getBody() {
			return body;
		}

		public String getLastModified() {
			return lastModified;
		}
	}

	public final static class NotModified<T> extends Result<T> {
		public NotModified() {
		}
	}

	public final static class Error<T> extends Result<T> {
		private final String message;

		public Error(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
