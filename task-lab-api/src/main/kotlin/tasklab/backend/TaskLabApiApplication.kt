package tasklab.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * アプリケーションの起動クラス
 * 自動設定・コンポーネントの探索・Bean定義の機能を有効にするメタアノテーション
 * 設定しないとコンポーネントの検索ができず起動・テストが失敗する
 *
 * @EnableAutoConfiguration
 * クラスパス上のライブラリなどを読み取り、
 * Spring Boot が自動でアプリケーションの設定を行ってくれる機能（自動設定）を有効にします。
 *
 * @ComponentScan
 * アノテーションが付いたクラスと同じパッケージ、およびその配下のパッケージから、
 * @Component、@Service、@Repository、@Controller などのコンポーネント（Bean）を探して自動登録します。
 *
 * @SpringBootConfiguration
 * このクラスが Spring の設定クラスであることを示します（内部的には @Configuration を内包しています）。
 */
@SpringBootApplication(
    scanBasePackages = [
        "tasklab", // 自モジュール以外にもtask-lab-core、task-lab-infrastructureもスキャン対象にする

    ],
)
class TaskLabApiApplication

fun main(args: Array<String>) {
    runApplication<TaskLabApiApplication>(*args)
}
