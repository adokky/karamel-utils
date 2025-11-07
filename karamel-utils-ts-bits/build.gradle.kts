plugins {
    alias(libs.plugins.quick.mpp)
    alias(libs.plugins.quick.publish)
}

version = "0.1.1"

dependencies {
    commonMainImplementation(project(":karamel-utils-core"))
    commonMainImplementation(libs.bitvector)
}

mavenPublishing {
    pom {
        name = project.name
        description = "Type-safe bit schema declaration"
        inceptionYear = "2025"
    }
}