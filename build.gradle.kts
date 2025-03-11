// For anonymous service without DB connection: https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.4.2&packaging=jar&jvmVersion=17&groupId=org.newlife&artifactId=iso8583parser&name=iso8583parser&description=iso8583parser&packageName=org.newlife.iso8583parser&dependencies=web,lombok
// Gradle User Manual: https://docs.gradle.org/current/userguide/userguide.html

plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.newlife"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.jpos:jpos:2.1.8")
	implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
