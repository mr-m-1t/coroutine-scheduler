package io.github.mrm1t.coroutineScheduler

interface TaskOrchestrator<T: Any> {

    /**
     * Begin executing tasks
     * TODO: this should return the launched coroutine
     */
    suspend fun start()

    /**
     * Add a task
     */
    fun addTask(tag: T, init: Task<T>.() -> Unit)

    companion object {
        /**
         * Create a new task orchestrator
         */
        fun <T: Any> taskOrchestrator(init: TaskOrchestrator<T>.() -> Unit): TaskOrchestrator<T> {
            return TaskOrchestratorImpl<T>(emptyList(), emptyList()).apply(init)
        }
    }
}
