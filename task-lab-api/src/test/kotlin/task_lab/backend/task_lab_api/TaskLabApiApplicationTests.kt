package task_lab.backend.task_lab_api

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

// TODO: Testcontainers を導入したら @Disabled を外す。
//  spring-boot-docker-compose は developmentOnly のためテストのクラスパスに載らず、
//  仮に載せても spring.docker.compose.skip.in-tests が既定 true なのでコンテナは起動しない。
//  そのため現状このテストは DataSource を生成できず必ず失敗する。
@Disabled("DataSource が必要。Testcontainers 導入後に有効化する")
@SpringBootTest
class TaskLabApiApplicationTests {

	@Test
	fun contextLoads() {
	}

}
