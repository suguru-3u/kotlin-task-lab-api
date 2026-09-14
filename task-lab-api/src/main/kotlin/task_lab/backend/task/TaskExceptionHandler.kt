package task_lab.backend.task

import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// TODO: ProblemDetailについて調べて調査する

@RestControllerAdvice
class TaskExceptionHandler {

    // TODO: レスポンスの型に種類がありそう
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "サーバーでエラーが発生")
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ProblemDetail {
        // TODO: エラーについてログを出力するようにする。loggerとか？
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
            title = "Bad Request"
            detail = "Invalid request"
        }
    }
}
