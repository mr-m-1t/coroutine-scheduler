package io.github.mrm1t.coroutineScheduler

/**
 * An orchestrator for work.
 * Add tasks and dependency information to this orchestrator.
 * Calling [start] executes the tasks, with prerequisites being called first.
 *
 * [T] is the type of the tag used to distinguish tasks.
 */
public interface TaskOrchestrator<T : Any> {
    /**
     * Begin executing tasks
     * This function returns once all tasks are executed
     * Parallelism is controlled by the dispatcher used to call this function
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
