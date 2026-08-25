package tasklab.core

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import io.kotest.core.spec.style.FreeSpec

// TODO: archunitを試しに導入。今はあまり効果ないかも。。マイクロサービス化が進んだ際に再度使用を検討する

object ImportedClasses {
    val production: JavaClasses by lazy {
        ClassFileImporter()
            .withImportOption(ImportOption.DoNotIncludeTests())
            .importPackages("tasklab.core")
    }
}

class ArchitectureTest : FreeSpec({

    val importedClasses = ImportedClasses.production

    "ドメイン層はフレームワークに依存しない" {
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                "org.springframework..",
                "jakarta.persistence..",
                "com.fasterxml.jackson.."
            )
            .check(importedClasses)
    }

    "パッケージ間に循環参照がない" {
        slices()
            .matching("tasklab.core.(*)..")
            .should().beFreeOfCycles()
            .check(importedClasses)
    }
})
