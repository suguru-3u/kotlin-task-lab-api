package tasklab.core.task.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TaskStatusTest :
    FreeSpec({
        "正常系" - {
            "スタート状態を作成できること" {
                val taskStatus = TaskStatus.start()
                taskStatus.value shouldBe TaskStatus.Status.Start
            }

            "スタート状態から完了状態に遷移できること" {
                val taskStatus = TaskStatus.start().complete()
                taskStatus.value shouldBe TaskStatus.Status.Complete
            }

            "再開の状態から完了状態に遷移できること" {
                val taskStatus = TaskStatus.start().complete().reopen().complete()
                taskStatus.value shouldBe TaskStatus.Status.Complete
            }

            "完了状態から再開状態に遷移できること" {
                val taskStatus = TaskStatus.start().complete().reopen()
                taskStatus.value shouldBe TaskStatus.Status.Reopen
            }
        }

        "異常系" - {
            "完了の状態から完了の状態になれないこと" {
                val completeTask = TaskStatus.start().complete()
                shouldThrow<IllegalStateException> {
                    completeTask.complete()
                }
            }

            "スタートの状態から再開の状態になれないこと" {
                val startTask = TaskStatus.start()
                shouldThrow<IllegalStateException> {
                    startTask.reopen()
                }
            }

            "再開の状態から再開の状態になれないこと" {
                val reopenTask = TaskStatus.start().complete().reopen()
                shouldThrow<IllegalStateException> {
                    reopenTask.reopen()
                }
            }
        }
    })
