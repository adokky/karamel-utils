import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

plugins {
    id("io.github.adokky.quick-mpp") version libs.versions.quickMpp
    id("io.github.adokky.quick-publish") version libs.versions.quickMpp
}

version = "0.1"

dependencies {
    commonTestImplementation("io.github.adokky", "equals-tester", libs.versions.equalsTester.get())
}

mavenPublishing {
    pom {
        description = "Collection of small platform-agnostic Kotlin utilities"
        inceptionYear = "2025"
    }
}

kover.reports.verify.rule {
    bound {
        aggregationForGroup.set(AggregationType.COVERED_PERCENTAGE)
        coverageUnits.set(CoverageUnit.LINE)
        minValue = 90
    }
}
