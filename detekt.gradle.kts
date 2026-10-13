detekt {
    toolVersion = "1.23.6"
    input = files("app/src/main/kotlin")
    filters = ".*/test/.*,.*/androidTest/.*,.*/build/.*,.*/generated/.*"
    config = files("detekt.yml")
    baseline = file("detekt-baseline.xml")
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

tasks.named("detekt") {
    dependsOn("detektGenerateBaseline")
}