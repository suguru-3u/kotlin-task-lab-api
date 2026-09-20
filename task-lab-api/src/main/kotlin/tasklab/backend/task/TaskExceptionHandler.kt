package tasklab.backend.task

import org.apache.coyote.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// TODO: ProblemDetailについて調べて調査する

@RestControllerAdvice
class TaskExceptionHandler {
    private val logger = LoggerFactory.getLogger(TaskExceptionHandler::class.java)

    // TODO: レスポンスの型に種類がありそう
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ProblemDetail {
        logger.error(ex.message, ex)
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR).apply {
            title = "INTERNAL SERVER ERROR"
            detail = "SERVER ERROR"
        }
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ProblemDetail {
        logger.error(ex.message, ex)
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
            title = "Bad Request"
            detail = "Invalid request"
        }
    }
}
