package io.github.mrm1t.coroutineScheduler

/**
 * A task to execute along with information about other tasks which should be executed before this one.
 *
 * [T] is the type of tag used to distinguish tasks
 */
public interface Task<T : Any> {
    /**
     * Add dependencies which must be completed before executing this task
     */
    public fun dependsOn(vararg tags: T)

    /**
     * Set the code block to execute
     */
    public fun block(blk: suspend () -> Unit)
}
