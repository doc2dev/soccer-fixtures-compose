package ke.eston.soccerfixtures.domain.repository

sealed interface OperationOutcome<out T : Any> {
    class Success<T : Any>(
        val outcome: T
    ) : OperationOutcome<T>

    class Failure(
        val error: Error
    ) : OperationOutcome<Nothing>
}

data class Error(
    val code: Int,
    val message: String?
)