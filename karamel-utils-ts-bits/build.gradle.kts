plugins {
    id("io.github.adokky.quick-mpp") version libs.versions.quickMpp
    id("io.github.adokky.quick-publish") version libs.versions.quickMpp
}

version = "0.1"

dependencies {
    commonMainImplementation(project(":karamel-utils-core"))
    commonMainImplementation("io.github.adokky", "bitvector-core", libs.versions.bitvector.get())
}

mavenPublishing {
    pom {
        name = project.name
        description = "Type-safe bit schema declaration"
        inceptionYear = "2025"
    }
}