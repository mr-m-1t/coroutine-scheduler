package io.github.mrm1t.coroutineScheduler

interface Task<T: Any> {

    /**
     * Add dependencies which must be completed before executing this task
     */
    fun dependsOn(vararg tags: T)

    /**
     * Set the code block to execute
     */
    fun block(blk: suspend () -> Unit)
}
