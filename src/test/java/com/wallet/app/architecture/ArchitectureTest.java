package com.wallet.app.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {
  private final JavaClasses CLASSES =
      new ClassFileImporter()
          .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
          .importPackages("com.wallet.app");

  @Test
  void domain_should_not_depend_on_outer_layers_or_frameworks() {
    noClasses()
        .that()
        .resideInAPackage("..wallet.domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(
            "..wallet.application..",
            "..wallet.adapter..",
            "org.springframework..",
            "jakarta.persistence..")
        .check(CLASSES);
  }

  @Test
  void application_should_not_depend_on_adapters() {
    noClasses()
        .that()
        .resideInAnyPackage("..wallet.application..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("..wallet.adapter..")
        .check(CLASSES);
  }
}
