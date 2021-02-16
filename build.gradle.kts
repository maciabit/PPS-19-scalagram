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

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    // Apply the Scalafmt plugin to add tasks related to code formatting
    id("cz.alenkacz.gradle.scalafmt") version "1.14.0"
}

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
