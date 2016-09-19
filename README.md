# jenkins-template
Template for use of puppet CI with JobDSL. It is a simple two step process

1. Merge this template in your repo
2. Register your repo in `ci-job-configs`

### Jenkins template
Copy over this repo to get the directory layout that our CI system expects. There is one `mother job` per jenkins master that will scan and create the jobs based on this directory structure.

**required files:**

    (root)
     +- jenkins                     # main directory for CI
         +- seeds                   # job dsl directory that will get scanned to create pipelines
              +- exampleJobs.groovy         # script using the job DSL to create the CI pipeline
              +- anotherExampleJob.groovy   # you can have more than one groovy file

**note** All the other files are for enabling local development and test

## Decentralized CI repositories
Previously the job definitions were all centralized and saved in the puppetlabs/ci-job-config repo with scripts written in YAML for consumption by JJB. We are now supporting decentralized scripts written in groovy and using Job DSL.

### Registering your repo
Registering your repo is a simple step that will let the system know it needs to scan your repo and all the branches and automatically create the necessary jobs and views on one of the jenkins masters. See registering your repo [registering repo][registering-repo]

### Using shared libraries
#### Job DSL libraries
Abstractions on top of the default JobDSL language for puppet specific jobs are created and maintained in a puppetlabs github repo [jenkins-jobdsl-lib][jenkins-jobdsl-lib] that library is distributed in our nexus instance and can be pinned by your project by setting the value of `puppetJobDslLibVersion` in the file `gradle.properties` 
See https://github.com/puppetlabs/jenkins-template/blob/master/gradle.properties#L2

## Local Development aka what is all that Gradle stuff?
The gradle build tool is used to test the validity of the groovy files created locally without having to commit or access a jenkins instance. 
This also gives confidence that the scripts are valid and/or can use the shared libraries.
### gradlew
Gradle provides a thin library called a wrapper that anyone can use in linux or windows by calling `./gradlew.sh` or `gradlew.bat`
They do *not* require gradle to be installed on the target system. These are auto-generated files by gradle, so on a system that *does* include gradle you can generate the wrapper files by running `gradle wrapper`
### tests even without gradle installed locally
Run the `test` task to check that all the jobs in `jenkins/seeds/*.groovy` are referring to valid jobDSL syntax.
```
./gradlew test
:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:compileTestJava UP-TO-DATE
:compileTestGroovy
:processTestResources UP-TO-DATE
:testClasses
:test

SeedsValidityTest > Generates XML for exampleJob.groovy without exception STANDARD_OUT
    Processing provided DSL script
    Test generated 2 jobs
    production_jenkins-jobdsl-plugin_standard_master
    production_jenkins-jobdsl-plugin_nostandard_2016.5.x

BUILD SUCCESSFUL

Total time: 5.387 secs
```
### debugXML even without gradle installed locally
Run the `debugXML` task with `-Psource=jenkins/seeds/<myfile>.groovy` to generate the job XML locally in the present working directory
```
./gradlew debugXML -Psource=jenkins/seeds/exampleJob.groovy

:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:debugXML
Processing DSL script jenkins/seeds/exampleJob.groovy
Sep 19, 2016 3:38:08 PM org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite$PojoCachedMethodSiteNoUnwrap invoke
INFO: From jenkins/seeds/exampleJob.groovy, Generated item: GeneratedJob{name='production_jenkins-jobdsl-plugin_standard_master'}
Sep 19, 2016 3:38:08 PM org.codehaus.groovy.runtime.callsite.PojoMetaMethodSite$PojoCachedMethodSiteNoUnwrap invoke
INFO: From jenkins/seeds/exampleJob.groovy, Generated item: GeneratedJob{name='production_jenkins-jobdsl-plugin_nostandard_2016.5.x'}

BUILD SUCCESSFUL

Total time: 3.249 secs
```

[registering-repo]: https://github.com/puppetlabs/ci-job-configs/blob/master/doc/cinext/registering-repos.md
[jenkins-jobdsl-lib]: https://github.com/puppetlabs/jenkins-jobdsl-lib
