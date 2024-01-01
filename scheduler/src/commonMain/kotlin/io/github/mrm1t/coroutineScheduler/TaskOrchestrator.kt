package io.github.mrm1t.coroutineScheduler

public interface TaskOrchestrator<T : Any> {
    /**
     * Begin executing tasks
     * This function returns once all tasks are executed
     */
    public suspend fun start()

    /**
     * Add a task
     */
    public fun addTask(
        tag: T,
        init: Task<T>.() -> Unit,
    )

    public companion object {
        /**
         * Create a new task orchestrator
         */
        public fun <T : Any> taskOrchestrator(init: TaskOrchestrator<T>.() -> Unit): TaskOrchestrator<T> {
            return TaskOrchestratorImpl<T>(emptyList(), emptyList()).apply(init)
        }
    }
}
