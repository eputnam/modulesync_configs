# jenkins-template
Template for use of puppet CI. Simple two step process

1. Merge this template in your repo
2. Register your repo

## Decentralized CI repositories
Previously the job definitions were all centralized and saved in the puppetlabs/ci-job-config repo with scripts written in YAML for consumption by JJB. We are now supporting decentralized scripts written in groovy and using Job DSL.

### Jenkins template
Copy over this repo to get the directory layout that our CI system expects. There is one `mother job` per jenkins master that will scan and create the jobs based on this directory structure.

    (root)
     +- jenkins                     # main directory for CI
         +- seeds                   # job dsl directory that will get scanned to create pipelines
              +- bar.groovy         # script using the job DSL to create the CI pipeline


### Registering your repo
Registering your repo is a simple step that will let the system know it needs to scan your repo and all the branches and automatically create the necessary jobs and views on one of the jenkins masters. See registering your repo [registering repo][registering-repo]

### Using shared libraries
#### Job DSL libraries
Abstractions on top of the default JobDSL language for puppet specific jobs are created and maintained in a puppetlabs github repo [jenkins-jobdsl-lib][jenkins-jobdsl-lib]

## Testing locally
The gradle build tool is used to test the validity of the groovy files created locally. This is to give confidence that the scripts are valid and can use the shared libraries.

[registering-repo]: https://github.com/puppetlabs/ci-job-configs/blob/master/doc/cinext/registering-repos.md
[jenkins-jobdsl-lib]: https://github.com/puppetlabs/jenkins-jobdsl-lib
