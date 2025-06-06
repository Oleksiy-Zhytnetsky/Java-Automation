package ua.edu.ukma.Zhytnetsky;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "count-classes", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ReportClassCounterMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        if (!this.outputDirectory.exists()) {
            getLog().error("Output directory does not exist: " + outputDirectory.getAbsolutePath());
            return;
        }

        final File rootPackage = new File(outputDirectory, "ua/edu/ukma/Zhytnetsky");
        if (!rootPackage.exists()) {
            getLog().error("Target package directory not found");
            return;
        }

        final File[] classFiles = rootPackage.listFiles((dir, name) -> name.endsWith(".class"));
        if (classFiles == null) {
            getLog().warn("No class files found");
            return;
        }

        getLog().info("### Report Summary Plugin:");
        getLog().info("\t- Total .class files: " + classFiles.length);
        getLog().info("\t- Found in package: ua.edu.ukma.Zhytnetsky");
    }

}
