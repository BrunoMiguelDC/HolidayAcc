package ciai.holidayacc.application.utils
import org.springframework.http.HttpStatus

/**
 *
 * Represents the result of an operation, either wrapping a result of the given type,
 * or an error.
 *
 * @param <T> type of the result value associated with success
 */
interface Result<T> {
    /**
     *
     * Service errors:
     * OK - no error, implies a non-null result of type T, except for Void operations
     * CONFLICT - something is being created but already exists
     * NOT_FOUND - access occurred to something that does not exist
     * INTERNAL_ERROR - something unexpected happened
     */
    /**
     * Tests if the result is an error.
     */
    fun isOK(): Boolean

    /**
     * obtains the payload value of this result
     * @return the value of this result.
     */
    fun value(): T

    /**
     *
     * obtains the error code of this result
     * @return the error code
     *
     */
    fun error(): HttpStatus


    companion object{
        /**
         * Convenience method for returning non error results of the given type
         * @return the value of the result
         */
        fun <T> ok(result: T): Result<T> {
            return OkResult(result)
        }

        /**
         * Convenience method for returning non error results without a value
         * @return non-error result
         */
        fun <T> ok():  Result<T> {
            return OkResult(null)
        }

        /**
         * Convenience method used to return an error
         * @return error result
         */
        fun <T> error(error: HttpStatus): Result<T> {
            return ErrorResult(error)
        }
    }
}

/*
 *
 */
class OkResult<T>(private val result: T?) : Result<T> {
    override fun isOK(): Boolean {
        return true
    }

    override fun value(): T {
        return result!!
    }

    override fun error(): HttpStatus {
        return HttpStatus.OK
    }

    override fun toString(): String {
        return ""
    }
}

class ErrorResult<T>(private val error: HttpStatus) : Result<T> {
    override fun isOK(): Boolean {
        return false
    }

    override fun value(): T {
        throw RuntimeException("Attempting to extract the value of an Error: $error")
    }

    override fun error(): HttpStatus {
        return error
    }

    override fun toString(): String {
        return "($error)"
    }
}
