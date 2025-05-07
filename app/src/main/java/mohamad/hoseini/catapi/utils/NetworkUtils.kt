package mohamad.hoseini.catapi.utils

import retrofit2.HttpException
import java.io.IOException

object NetworkUtils {


    fun getErrorMessageFromHttpStatusCode(statusCode: Int): String {
        return when (statusCode) {
            429 -> "Too many requests. Please try again later."
            404 -> "Data not found. Please check your request."
            500 -> "Server error. Please try again later."
            503 -> "Service unavailable. Please try again later."
            else -> "Failed to fetch data. Status code: $statusCode"
        }
    }


    fun handleError(e: Exception): String {
        val errorMessage = when (e) {
            is IOException -> "Network error. Please check your connection and try again."
            is HttpException -> "Something went wrong while fetching the data. Please try again later."
            else -> "An unexpected error occurred. Please try again later."
        }
        return errorMessage
    }

}
