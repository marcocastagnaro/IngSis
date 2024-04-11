tasks.register("copyPreCommitGitHook") {
//    shouldRunAfter("build")
    doFirst {
        println("This task is happening")
        copy {
            from("./scripts/pre-commit")
            into(".git/hooks")
            fileMode = 493
        }
    }
}
