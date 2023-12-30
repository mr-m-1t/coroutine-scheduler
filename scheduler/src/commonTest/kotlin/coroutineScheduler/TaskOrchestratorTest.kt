package coroutineScheduler

import io.github.mrm1t.coroutineScheduler.TaskOrchestrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class TaskOrchestratorTest {

    @Test
    fun sampleTestDemonstratingDslUsageMultiThreaded() =
        runTest {
            // specifying a dispatcher since the default one is single-threaded
            launch(Dispatchers.Default) {
                demoDslUsage()
            }.join()
        }


    private suspend fun demoDslUsage() {
        TaskOrchestrator.taskOrchestrator<String> {
            addTask("1") {
                block { doWork(10) }
            }
            addTask("2") {
                dependsOn("1")
                block { doWork(40) }
            }
            addTask("3") {
                dependsOn("1")
                block { doWork(100) }
            }
            addTask("4") {
                dependsOn("2", "3")
                block {
                    doWork(10)
                }
            }
            addTask("5") {
                dependsOn("2", "4")
                block { doWork(50) }
            }
        }.start()
    }

    private suspend fun doWork(num: Int) =
        coroutineScope {
            delay(num.milliseconds)
        }
}
