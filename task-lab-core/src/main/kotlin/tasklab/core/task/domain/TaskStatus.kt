package tasklab.core.task.domain

// taskのステータスを管理するクラス
// スタートは外部から作成できて完了にする場合、関数を通してじゃないと完了にすることができない

class TaskStatus private constructor(
    val value: Status
) {

    enum class Status {
        Start, Complete, Reopen
    }

    companion object {
        fun start(): TaskStatus = TaskStatus(Status.Start)
    }

    fun complete(): TaskStatus {
        if (value != Status.Start) throw IllegalStateException("Task can only be completed from Start status.")
        return TaskStatus(Status.Complete)
    }

    fun reopen(): TaskStatus {
        if (value != Status.Complete) throw IllegalStateException("Task can only reopen in Start status.")
        return TaskStatus(Status.Reopen)
    }
}
