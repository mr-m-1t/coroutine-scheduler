package io.github.mrm1t.coroutineScheduler

interface Task<T: Any> {

    /**
     * Sets the tag for this task
     */
    fun tag(tag: T)

    /**
     * Add dependencies which must be completed before executing this task
     */
    fun dependsOn(vararg tags: T)

    /**
     * Set the code block to execute
     */
    fun block(blk: suspend () -> Unit)
}
