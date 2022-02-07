apply plugin: 'maven-publish'
apply plugin: 'maven'

// Using extra variables instead of def so the values are usable in the project that applies the script
project.ext{
    baseRepositoryURL = "https://artifactory.skilling.com/artifactory"
    publicRepository = "$baseRepositoryURL/libs-release"
    releaseRepository = "$baseRepositoryURL/libs-release-local"
    snapshotRepository = "$baseRepositoryURL/libs-snapshot-local"
}

assert project.hasProperty('repositoryUser') : "repositoryUser not defined. Specify the property in ~/.gradle/gradle.properties"
assert project.hasProperty('repositoryPassword') : "repositoryPassword not defined. Specify the property in ~/.gradle/gradle.properties"

repositories {
    maven {
        name "Propero Repository"
        url project.publicRepository
        credentials {
            username = "${repositoryUser}"
            password = "${repositoryPassword}"
        }
    }
}

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: "${project.releaseRepository}") {
                authentication(userName: "${repositoryUser}", password: "${repositoryPassword}");
            }
            snapshotRepository(url: "${project.snapshotRepository}") {
                authentication(userName: "${repositoryUser}", password: "${repositoryPassword}");
            }
        }
    }
}