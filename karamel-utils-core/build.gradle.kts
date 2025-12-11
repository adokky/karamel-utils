import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

plugins {
    alias(libs.plugins.quick.mpp)
    alias(libs.plugins.quick.publish)
}

version = "0.2.1"

dependencies {
    commonTestImplementation(libs.equalsTester)
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