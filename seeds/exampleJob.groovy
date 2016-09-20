// to use the shared library import the classes you need here
import puppet.jenkins.jobdsl.builders.BaseJobBuilder

def job1 = new BaseJobBuilder(
  github_project: "jenkins-jobdsl-plugin",
  description: "This is a simple job"
).build(this)

def job2 = new BaseJobBuilder(
  github_project: "jenkins-jobdsl-plugin",
  git_branch: "2016.5.x",
  custom_dsl_closure: {
    steps {
      shell('echo Hello World')
    }
  }
)

//can also change fields on the object
job2.job_purpose = "nostandard"

job2.build(this)
