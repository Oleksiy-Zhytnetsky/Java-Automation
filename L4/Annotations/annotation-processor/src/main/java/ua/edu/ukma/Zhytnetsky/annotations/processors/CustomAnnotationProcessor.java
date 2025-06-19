package ua.edu.ukma.Zhytnetsky.annotations.processors;

import com.palantir.javapoet.*;
import ua.edu.ukma.Zhytnetsky.annotations.GenerateBuilder;
import ua.edu.ukma.Zhytnetsky.annotations.GenerateFieldConstants;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "ua.edu.ukma.Zhytnetsky.annotations.GenerateBuilder",
        "ua.edu.ukma.Zhytnetsky.annotations.GenerateFieldConstants"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class CustomAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        this.messager = env.getMessager();
        this.filer = env.getFiler();
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith(GenerateBuilder.class)) {
            if (elem.getKind() != ElementKind.CLASS) {
                continue;
            }

            final TypeElement classElement = (TypeElement)elem;
            try {
                generateBuilderFor(classElement);
            }
            catch (final IOException exception) {
                this.messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed to write builder class: " + exception.getMessage()
                );
            }
        }

        for (final Element elem : roundEnv.getElementsAnnotatedWith(GenerateFieldConstants.class)) {
            if (elem.getKind() != ElementKind.CLASS) {
                continue;
            }

            final TypeElement classElement = (TypeElement)elem;
            try {
                generateFieldConstantsFor(classElement);
            }
            catch (final IOException exception) {
                this.messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed to write field constants class: " + exception.getMessage()
                );
            }
        }

        return true;
    }

    private void generateBuilderFor(final TypeElement classElement) throws IOException {
        final String targetPackage = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
        final String originalName = classElement.getSimpleName().toString();
        final String targetBuilderName = originalName + "Builder";

        final TypeSpec.Builder classBuilder = TypeSpec.classBuilder(targetBuilderName)
                .addModifiers(Modifier.PUBLIC);

        for (final VariableElement field : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
            final String fieldName = field.getSimpleName().toString();
            final TypeName fieldType = TypeName.get(field.asType());

            classBuilder.addField(fieldType, fieldName, Modifier.PRIVATE);

            final MethodSpec setterMethod = MethodSpec.methodBuilder(fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.get(targetPackage, targetBuilderName))
                    .addParameter(fieldType, fieldName)
                    .addStatement("this.$N = $N", fieldName, fieldName)
                    .addStatement("return this")
                    .build();
            classBuilder.addMethod(setterMethod);
        }

        final MethodSpec.Builder buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(targetPackage, originalName));

        final String args = ElementFilter.fieldsIn(classElement.getEnclosedElements()).stream()
                .map(f -> f.getSimpleName().toString())
                .collect(Collectors.joining(", "));
        buildMethod.addStatement("return new $N($L)", originalName, args);
        classBuilder.addMethod(buildMethod.build());

        JavaFile.builder(targetPackage, classBuilder.build())
                .build()
                .writeTo(this.filer);
    }

    private void generateFieldConstantsFor(final TypeElement classElement) throws IOException {
        final String targetPackage = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
        final String originalName = classElement.getSimpleName().toString();
        final String targetInterfaceName = originalName + "FieldConstants";

        final TypeSpec.Builder interfaceBuilder = TypeSpec.interfaceBuilder(targetInterfaceName)
                .addModifiers(Modifier.PUBLIC);

        final List<VariableElement> classFields = ElementFilter.fieldsIn(classElement.getEnclosedElements());
        for (final VariableElement field : classFields) {
            final String fieldName = field.getSimpleName().toString();
            final String constantName = fieldName
                    .replaceAll("([a-z])([A-Z])", "$1_$2")
                    .toUpperCase();

            final FieldSpec fieldSpecification = FieldSpec.builder(
                    ClassName.get(String.class),
                    constantName,
                    Modifier.PUBLIC,
                    Modifier.STATIC,
                    Modifier.FINAL
            ).initializer("$S", fieldName).build();
            interfaceBuilder.addField(fieldSpecification);
        }

        JavaFile.builder(targetPackage, interfaceBuilder.build())
                .build()
                .writeTo(this.filer);
    }

}
