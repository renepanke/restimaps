plugins {
    id("io.micronaut.application") version "4.5.0"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.aot") version "4.5.0"
    id("idea")
    id("org.jetbrains.kotlin.kapt") version "2.1.20"
    kotlin("jvm") version "2.1.20"
}

version = "0.1"
group = "io.github.renepanke"

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

repositories {
    mavenCentral()
}

val caffeineVersion = "3.2.0"
val nimbusJoseJwtVersion = "10.0.1"

dependencies {
    compileOnly("io.micronaut:micronaut-http-client")
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
    implementation("com.nimbusds:nimbus-jose-jwt:$nimbusJoseJwtVersion")
    implementation("io.micronaut.email:micronaut-email-javamail")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    // ~~~ MICRONAUT METRICS ~~~
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")
    // ~~~ MICRONAUT METRICS ~~~
    // ~~~ GRAFANA PYROSCOPE ~~~
    implementation("io.pyroscope:agent:0.18.0")
    // ~~~ GRAFANA PYROSCOPE ~~~
    implementation("io.micronaut.controlpanel:micronaut-control-panel-ui")
    implementation("io.micronaut.controlpanel:micronaut-control-panel-management")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.eclipse.angus:angus-mail")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    kapt("io.micronaut.validation:micronaut-validation-processor")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut:micronaut-inject-java")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    implementation("io.micronaut.problem:micronaut-problem-json")
}


application {
    mainClass = "io.github.renepanke.restimaps.ApplicationK"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

kapt {
    generateStubs = false
    keepJavacAnnotationProcessors = true
    correctErrorTypes = true
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.renepanke.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


