package io.github.mrm1t.coroutineScheduler

interface TaskOrchestrator<T: Any> {

    /**
     * Begin executing tasks
     * TODO: this should return the launched coroutine
     */
    suspend fun start()

    /**
     * Add a task
     * TODO: this should force a tag to be set
     */
    fun addTask(init: Task<T>.() -> Unit)

    companion object {
        /**
         * Create a new task orchestrator
         */
        fun <T: Any> taskOrchestrator(init: TaskOrchestrator<T>.() -> Unit): TaskOrchestrator<T> {
            return TaskOrchestratorImpl<T>(emptyList(), emptyList()).apply(init)
        }
    }
}
