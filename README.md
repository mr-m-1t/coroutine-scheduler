## coroutine-scheduler
TODO: build badges

### Description
Every have a bunch of jobs that must be done in a certain order?
This library aims to make it easy to run jobs that rely on other independent jobs.

### How this library works
We create a Directed Acyclic Graph (DAG) with each job as a vertex, and each dependency as an edge.
The jobs are topologically sorted, and then started. Each job will wait for those it depends on before beginning its work.

### How to use this library:
TODO