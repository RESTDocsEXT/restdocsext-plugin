
package io.github.restdocsext.plugin.gradle

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory

/**
 * Configuration properties for Asciidoctor plugin.
 *
 * @author Paul Samsotha
 */

class AsciidoctorConfig {

    @InputDirectory
    File sourceDir

    @OutputDirectory
    File outputDir

    @Optional @Input
    Map attributes = [:]

    def sourceDir(File sourceDir) {
        this.sourceDir = sourceDir
    }

    def setSourceDir(File sourceDir) {
        this.sourceDir = sourceDir
    }

    def sourceDir() {
        this.sourceDir
    }

    def outputDir() {
        this.outputDir
    }

    def setOuputDir(File outputDir) {
        this.outputDir = outputDir
    }

    def outputDir(File outputDir) {
        this.outputDir = outputDir
    }

    def attributes(Map attributes) {
        this.attributes = attributes
    }

    def setAttributes(Map attributes) {
        this.attributes = attributes
    }

    def getAttributes() {
        this.attributes
    }
}
