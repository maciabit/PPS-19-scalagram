version = project.version

group = "io.github.maciabit"

plugins {
    java
    scala
    `java-library`
    `maven-publish`
    signing

    id("org.danilopianini.publish-on-central") version "0.4.2"
    id("com.diffplug.spotless") version "5.10.2"
    id("org.scoverage") version "5.0.0"
    id("org.danilopianini.git-sensitive-semantic-versioning") version "0.1.0"
    id("org.kordamp.gradle.scaladoc") version "0.40.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.5")

    implementation("com.lihaoyi:requests_2.13:0.6.5")

    implementation("io.circe:circe-core_2.13:0.14.0-M4")
    implementation("io.circe:circe-parser_2.13:0.14.0-M4")
    implementation("io.circe:circe-generic_2.13:0.14.0-M4")

    implementation("org.slf4j:slf4j-nop:1.7.30")
    implementation("com.typesafe.akka:akka-actor-typed_2.13:2.6.13")

    testImplementation("junit:junit:null")
    testImplementation("org.scalatest:scalatest_2.13:3.3.0-SNAP3")
    testImplementation("org.scalatestplus:junit-4-12_2.13:3.3.0.0-SNAP2")

    testRuntimeOnly("org.scala-lang.modules:scala-xml_2.13:1.3.0")
}

gitSemVer {
    version = computeGitSemVer()
}

spotless {
    scala {
        scalafmt("2.5.0").configFile(".scalafmt.conf")
        targetExclude("src/main/scala/PPS19/scalagram/examples/dsl/")
    }
}

scoverage {
    excludedPackages.addAll("PPS19.scalagram.examples", "PPS19.scalagram.examples.dsl")
}

publishOnCentral {
    projectUrl = "https://github.com/maciabit/${project.name}"
    scmConnection = "git:git@github.com:maciabit/${project.name}"
}

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
                    developer {
                        name.set("Gianni Tumedei")
                        email.set("gianni.tumedei@studio.unibo.it")
                        url.set("https://github.com/gianni-tumedei-studio-unibo")
                    }
                    developer {
                        name.set("Francesco Boschi")
                        email.set("francesco.boschi2@studio.unibo.it")
                        url.set("https://github.com/FrancescoBoschi")
                    }
                    developer {
                        name.set("Filippo Pistocchi")
                        email.set("filippo.pistocchi4@studio.unibo.it")
                        url.set("https://github.com/pistocchifilippo")
                    }
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
}

tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.apply {
        additionalParameters = listOf("-Xfatal-warnings", "-Ywarn-unused", "-feature", "-language:implicitConversions")
    }
}

tasks.compileScala.configure {
   dependsOn(tasks.getByName("spotlessCheck"))
}
