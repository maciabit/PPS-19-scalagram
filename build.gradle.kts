/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Scala library project to get you started.
 * For more details take a look at the Scala plugin chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.6.1/userguide/scala_plugin.html
 */

plugins {
    // Apply the scala plugin to add support for Scala
    scala

    // Apply the java-library plugin for API and implementation separation
    `java-library`

    // Plugins needed in order to make Maven Central Gradle plugin works
    java
    `maven-publish`

    // Import GitSemVer plugin
    id("org.danilopianini.git-sensitive-semantic-versioning") version "0.1.0"

    // Import Maven Central Gradle plugin
    id("org.danilopianini.publish-on-central") version "0.4.0"

    // Import Spotless plugin for code convention formatting
    id("com.diffplug.spotless") version "5.10.2"

    // Import Gradle Scoverage plugin
    id("org.scoverage") version "5.0.0"

    // Import Gradle Scalafix plugin
    //id("io.github.cosmicsilence.scalafix") version "0.1.5"
}

//gitSemVer {
//    version = computeGitSemVer()
//}

repositories {
    // Use mavenCentral for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use Scala 2.13 in our library project
    implementation("org.scala-lang:scala-library:2.13.2")

    // Use requests to perform HTTP requests
    implementation("com.lihaoyi:requests_2.13:0.6.5")

    // Use Circe to perform object <-> JSON conversions
    implementation("io.circe:circe-core_2.13:0.14.0-M3")
    implementation("io.circe:circe-parser_2.13:0.14.0-M3")
    implementation("io.circe:circe-generic_2.13:0.14.0-M3")

    // Use Akka for the polling infrastructure
    implementation("org.slf4j:slf4j-nop:1.7.30")
    implementation("com.typesafe.akka:akka-actor-typed_2.13:2.6.11")

    // Use Scalatest for testing our library
    testImplementation("junit:junit:null")
    testImplementation("org.scalatest:scalatest_2.13:3.1.2")
    testImplementation("org.scalatestplus:junit-4-12_2.13:3.1.2.0")

    // Need scala-xml at test runtime
    testRuntimeOnly("org.scala-lang.modules:scala-xml_2.13:1.2.0")
}

spotless {
    scala {
        scalafmt("2.5.0").configFile(".scalafmt.conf")
        targetExclude("src/main/scala/PPS19/scalagram/examples/dsl/")
    }
}


// Run compileScala only if all target files are correctly formatted
tasks.compileScala.configure {
   dependsOn(tasks.getByName("spotlessCheck"))
}

//tasks.compileTestScala.configure {
  //  dependsOn(tasks.getByName("checkTestScalafmt"))
//}

group = "io.github.maciabit"

publishOnCentral {
    // The following values are the default, if they are ok with you, just omit them
    //rojectDescription = "No description provided"
    //projectLongName = project.name
    //licenseName = "Apache License, Version 2.0"
    //licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0"
    projectUrl = "https://github.com/maciabit/${project.name}"
    scmConnection = "git:git@github.com:maciabit/${project.name}"
    /*
     * The plugin is pre-configured to fetch credentials for Maven Central from the environment
     * Username from: MAVEN_CENTRAL_USERNAME
     * Password from: MAVEN_CENTRAL_PASSWORD
     *
     * In case of failure, it falls back to properties mavenCentralUsername and mavenCentralPassword respectively
     */
    /*
     * This publication can be sent to other destinations, e.g. GitHub
    repository("https://maven.pkg.github.com/OWNER/REPOSITORY", "GitHub") {
        user = System.getenv("GITHUB_USERNAME")
        password = System.getenv("GITHUB_TOKEN")
    }
    */
    /*
     * You may also want to configure publications created by other plugins
     * like the one that goes on Central. Typically, for instance, for publishing
     * Gradle plugins to Maven Central.
     * It can be done as follows.

    publishing {
        publications {
            withType<MavenPublication> {
                configurePomForMavenCentral()
            }
        }
    }
    */
}
/*
 * Developers and contributors must be added manually
 */
publishing {
    publications {
        withType<MavenPublication> {
            pom {
                developers {
                    developer {
                        name.set("Mattia Rossi")
                        email.set("mattia.rossi15@studio.unibo.it")
                        url.set("https://github.com/maciabit")
                    }
                }
            }
        }
    }
}
/*
 * The plugin automatically adds every publication to the list of objects to sign
 * The configuration of the signing process is left to the user, though,
 * as in a normal Gradle build.
 * In the following example, in-memory signing is configured.
 * For further options, please refer to: https://docs.gradle.org/current/userguide/signing_plugin.html
 */
signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
}

/*val scaladocJar by tasks.registering(Jar::class) {
    this.archiveCLassifier.set("scaladoc")
    from(tasks.scaladoc.get().outputDirectory)
}*/