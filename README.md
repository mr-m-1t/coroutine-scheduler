## coroutine-scheduler
TODO: build badges

### Description
Every have a bunch of jobs that must be done in a certain order?
This library aims to make it easy to run jobs that rely on other independent jobs.

### How this library works
We create a Directed Acyclic Graph (DAG) with each job as a vertex, and each dependency as an edge.
The jobs are topologically sorted, and then started. Each job will wait for those it depends on before beginning its work.

### How to use this library:
This library follows [semantic versioning](https://semver.org/spec/v2.0.0.html)

Add this library to your project, same as any other:
```kts
// build.gradle.kts
dependencies {
    implementation("io.github.mrm1t:scheduler:1.0.0")
}
```

Launch some work:
```kt
fun doWork(i: Int) {
    println(i) 
}

// create a task
val overallTask = TaskOrchestrator.taskOrchestrator {
    addTask("1") {
        block { doWork(10) }
    }
    addTask("2") {
        dependsOn("1")
        block { doWork(20) }
    }
    addTask("3)") {
        dependsOn("1")
        block { doWork(30) }
    }
    addTask("4") {
        dependsOn("2", "3")
        block { doWork(40); }
    }
    addTask("5") {
        dependsOn("2", "4")
        block { doWork(50) }
    }
}

// start the task
overallTask.start()

// output:
// 10, 20, 30, 40, 50
// or
// 10, 30, 20, 40, 50
```

That's it!

### Contributing
Contributions are welcome. See [CONTRIBUTING.md](CONTRIBUTING.md) for details
