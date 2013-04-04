package com.example.wheresmystuff.model;

/**
 * Possibilities for a register query
 * @author Steven Han
 */
public enum RegisterResult {
	ACCEPTED, INVALID_USERNAME, INVALID_PASS, INVALID_EMAIL, USER_ALREADY_EXISTS, DB_ERROR, NETWORK_ERROR
}
