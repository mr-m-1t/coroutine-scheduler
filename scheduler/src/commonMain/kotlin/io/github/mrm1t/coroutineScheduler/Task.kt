package io.github.mrm1t.coroutineScheduler

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
