package task_lab.backend.task_lab_api.task

import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TaskExceptionHandler {

    // TODO: レスポンスの型に種類がありそう
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "サーバーでエラーが発生")
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, "不正なリクエストです")
    }
}
