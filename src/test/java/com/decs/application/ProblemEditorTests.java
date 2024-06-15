package com.decs.application;

import com.microsoft.playwright.Locator;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("ProblemEditorTests")
public class ProblemEditorTests extends PlaywrightIT {
    private static BigDecimal bd;
    private static OutputStream outStream;

    @Test
    void visibleComponentsTest() {
        // Select Problem Editor View
        page.locator("//vaadin-side-nav-item[@id='problemEditorNavItem']").click();

        assertThat(page.locator("//vaadin-tab[normalize-space(.)='General']")).isVisible();
        page.locator("//vaadin-tab[normalize-space(.)='General']").click();

        assertThat(page.locator("vaadin-select label:has-text(\"Problem\")")).isVisible();
        assertThat(page.locator("vaadin-select label:has-text(\"Problem\")")).isEnabled();

        assertThat(page.locator("vaadin-select label:has-text(\"Distribution\")")).isVisible();
        assertThat(page.locator("vaadin-select label:has-text(\"Distribution\")")).isEnabled();

        assertThat(page.locator("span:has-text('Misc')")).isVisible();

        Locator jobs = page.locator("vaadin-integer-field label:has-text(\"Jobs\")");
        assertThat(jobs).isVisible();
        assertThat(jobs).isEnabled();

        Locator randomSeed = page.locator("vaadin-combo-box label:has-text(\"Random Seed\")");
        assertThat(randomSeed).isVisible();
        assertThat(randomSeed).isEnabled();

        assertThat(page.locator("span:has-text('Multithreading')")).isVisible();

        Locator evalThreads = page.locator("vaadin-integer-field label:has-text(\"Eval Threads\")");
        assertThat(evalThreads).isVisible();
        assertThat(evalThreads).isEnabled();

        Locator breedThreads = page.locator("vaadin-integer-field label:has-text(\"Breed Threads\")");
        assertThat(breedThreads).isVisible();
        assertThat(breedThreads).isEnabled();

        assertThat(page.locator("span:has-text('Checkpoint')")).isVisible();

        Locator checkpoint = page.locator("vaadin-select label:has-text(\"Checkpoint\")");
        assertThat(checkpoint).isVisible();
        assertThat(checkpoint).isEnabled();

        Locator modulo = page.locator("vaadin-integer-field label:has-text(\"Modulo\")");
        assertThat(modulo).isVisible();
        assertThat(modulo).isDisabled();

        Locator prefix = page.locator("vaadin-text-field label:has-text(\"Prefix\")");
        assertThat(prefix).isVisible();
        assertThat(prefix).isDisabled();

        assertThat(page.locator("//vaadin-tab[normalize-space(.)='Save']")).isVisible();
        page.locator("//vaadin-tab[normalize-space(.)='Save']").click();

        assertThat(page.locator("span:has-text('Problem Info')")).isVisible();

        Locator problemCode = page.locator("vaadin-text-field label:has-text(\"Problem Code\")");
        assertThat(problemCode).isVisible();
        assertThat(problemCode).isEnabled();

        Locator problemName = page.locator("vaadin-text-field label:has-text(\"Problem Name\")");
        assertThat(problemName).isVisible();
        assertThat(problemName).isEnabled();

        Locator problemType = page.locator("vaadin-text-field label:has-text(\"Problem Type\")");
        assertThat(problemType).isVisible();
        assertThat(problemType).isEnabled();

        Locator saveBtn = page.locator("vaadin-button:has-text('save')");
        assertThat(saveBtn).isVisible();
        assertThat(saveBtn).isEnabled();
    }

    @Test
    void saveProblemTest() {
        Locator locator1;
        Locator locator2;

        page.locator("//vaadin-side-nav-item[@id='problemEditorNavItem']").click();

        page.locator("//vaadin-tab[normalize-space(.)='General']").click();

        locator1 = page.locator("vaadin-select:has(label:has-text('Problem'))");
        locator1.click();
        locator2 = page.locator("vaadin-select-item:has-text(\"Boolean 11 Multiplexer (fast)\")");
        locator2.click();

        locator1 = page.locator("vaadin-select:has(label:has-text('Distribution'))");
        locator1.click();
        locator2 = page.locator("vaadin-select-item:has-text(\"LOCAL\")");
        locator2.click();

        page.locator("vaadin-integer-field label:has-text(\"Jobs\")").fill("2");

        page.locator("vaadin-combo-box label:has-text(\"Random Seed\")").fill("3847");


        page.locator("vaadin-integer-field label:has-text(\"Eval Threads\")").fill("3");

        page.locator("vaadin-integer-field label:has-text(\"Breed Threads\")").fill("3");

        // Simple Tab
        page.locator("//vaadin-tab[normalize-space(.)='Simple']").click();

        locator1 = page.locator("vaadin-select:has(label:has-text('Variable Type'))");
        locator1.click();
        locator2 = page.locator("vaadin-select-item:has-text(\"Evaluations\")");
        locator2.click();

        page.locator("vaadin-integer-field label:has-text(\"Value\")").fill("3000");

        page.locator("vaadin-integer-field label:has-text(\"Size\")").fill("2048");

        page.locator("vaadin-integer-field label:has-text(\"Duplicate Retries\")").fill("100");

        page.locator("vaadin-integer-field label:has-text(\"Elite\")").fill("6");

        // Koza Tab
        page.locator("//vaadin-tab[normalize-space(.)='Koza']").click();

        page.locator("//vaadin-integer-field[@id=\"initialCreationMinDepth\"]//input").fill("3");

        page.locator("//vaadin-integer-field[@id='initialCreationMaxDepth']//input").fill("5");

        page.locator("//vaadin-number-field[@id='initialCreationGrowProb']//input").fill("0.7");

        page.locator("//vaadin-number-field[@id='crossoverPipelineProb']//input").fill("0.3");

        page.locator("//vaadin-number-field[@id='reproductionPipelineProb']//input").fill("0.2");

        page.locator("//vaadin-integer-field[@id='crossoverPipelineMaxDepth']//input").fill("15");

        page.locator("//vaadin-integer-field[@id='crossoverPipelineTries']//input").fill("2");

        page.locator("//vaadin-integer-field[@id='pointMutationMaxDepth']//input").fill("14");

        page.locator("//vaadin-integer-field[@id='pointMutationTries']//input").fill("3");

        page.locator("//vaadin-integer-field[@id='tournamentSize']//input").fill("9");

        page.locator("//vaadin-integer-field[@id='subtreeMutationMinDepth']//input").fill("3");

        page.locator("//vaadin-integer-field[@id='subtreeMutationMaxDepth']//input").fill("2");

        page.locator("//vaadin-number-field[@id='kozaNodeSelectionTerminalsProb']//input").fill("0.2");

        page.locator("//vaadin-number-field[@id='kozaNodeSelectionNonTerminalsProb']//input").fill("0.97");

        page.locator("//vaadin-number-field[@id='kozaNodeSelectionRootProb']//input").fill("0.2");

        // Save Tab
        page.locator("//vaadin-tab[normalize-space(.)='Save']").click();

        locator1 = page.locator("//vaadin-text-field[label[contains(text(), \"Problem Code\")]]//input");
        locator1.fill("TestP");

        locator1 = page.locator("//vaadin-text-field[label[contains(text(), \"Problem Name\")]]//input");
        locator1.fill("Test Problem");

        locator1 = page.locator("//vaadin-text-field[label[contains(text(), \"Problem Type\")]]//input");
        locator1.fill("GP");

        locator1 = page.locator("vaadin-button:has-text('save')");
        locator1.click();

        page.waitForTimeout(1000);

        // Verifications

        File problemFolder = new File("src/main/resources/ECJ/params/problems/user/TestP");
        File problemFile = new File("src/main/resources/ECJ/params/problems/user/TestP/TestP.params");
        File ecFile = new File("src/main/resources/ECJ/params/problems/user/TestP/ec.params");
        File kozaFile = new File("src/main/resources/ECJ/params/problems/user/TestP/koza.params");
        File simpleFile = new File("src/main/resources/ECJ/params/problems/user/TestP/simple.params");
        File confFile = new File("src/main/resources/ECJ/params/problems/user/TestP/TestP.conf");

        assertTrue(problemFolder.exists());
        assertTrue(problemFile.exists());
        assertTrue(ecFile.exists());
        assertTrue(kozaFile.exists());
        assertTrue(simpleFile.exists());
        assertTrue(confFile.exists());

        try {
            ParameterDatabase paramDatabase = new ParameterDatabase(problemFile);
            assertNotNull(paramDatabase);

            assertEquals(3, paramDatabase.getInt(new Parameter("evalthreads"), new Parameter("evalthreads")));
            assertEquals(3, paramDatabase.getInt(new Parameter("breedthreads"), new Parameter("breedthreads")));

            assertEquals(3000, paramDatabase.getInt(new Parameter("evaluations"), new Parameter("evaluations")));
            assertEquals(2048, paramDatabase.getInt(new Parameter("pop.subpop.0.size"), new Parameter("pop.subpop.0.size")));
            assertEquals(100, paramDatabase.getInt(new Parameter("pop.subpop.0.duplicate-retries"), new Parameter("pop.subpop.0.duplicate-retries")));
            assertEquals(6, paramDatabase.getInt(new Parameter("breed.elite.0"), new Parameter("breed.elite.0")));
            assertEquals("false", paramDatabase.getString(new Parameter("breed.reevaluate-elites.0"), new Parameter("breed.reevaluate-elites.0")));
            assertEquals("false", paramDatabase.getString(new Parameter("breed.sequential"), new Parameter("breed.sequential")));

            assertEquals(3, paramDatabase.getInt(new Parameter("gp.koza.half.min-depth"), new Parameter("gp.koza.half.min-depth")));
            assertEquals(5, paramDatabase.getInt(new Parameter("gp.koza.half.max-depth"), new Parameter("gp.koza.half.max-depth")));
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("gp.koza.half.growp"), new Parameter("gp.koza.half.growp")));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            assertEquals(0.7, bd.doubleValue());
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("pop.subpop.0.species.pipe.source.0.prob"), new Parameter("pop.subpop.0.species.pipe.source.0.prob")));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            assertEquals(0.3, bd.doubleValue());
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("pop.subpop.0.species.pipe.source.1.prob"), new Parameter("pop.subpop.0.species.pipe.source.1.prob")));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            assertEquals(0.2, bd.doubleValue());
            assertEquals(15, paramDatabase.getInt(new Parameter("gp.koza.xover.maxdepth"), new Parameter("gp.koza.xover.maxdepth")));
            assertEquals(2, paramDatabase.getInt(new Parameter("gp.koza.xover.tries"), new Parameter("gp.koza.xover.tries")));
            assertEquals(14, paramDatabase.getInt(new Parameter("gp.koza.mutate.maxdepth"), new Parameter("gp.koza.mutate.maxdepth")));
            assertEquals(3, paramDatabase.getInt(new Parameter("gp.koza.mutate.tries"), new Parameter("gp.koza.mutate.tries")));
            assertEquals(9, paramDatabase.getInt(new Parameter("select.tournament.size"), new Parameter("select.tournament.size")));
            assertEquals(3, paramDatabase.getInt(new Parameter("gp.koza.grow.min-depth"), new Parameter("gp.koza.grow.min-depth")));
            assertEquals(2, paramDatabase.getInt(new Parameter("gp.koza.grow.max-depth"), new Parameter("gp.koza.grow.max-depth")));
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("gp.koza.ns.terminals"), new Parameter("gp.koza.ns.terminals")));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            assertEquals(0.2, bd.doubleValue());
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("gp.koza.ns.nonterminals"), new Parameter("gp.koza.ns.nonterminals")));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            assertEquals(0.97, bd.doubleValue());
            bd = new BigDecimal(paramDatabase.getDouble(new Parameter("gp.koza.ns.root"), new Parameter("gp.koza.ns.root")));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            assertEquals(0.2, bd.doubleValue());
        } catch (IOException e) {
            System.err.println("IO Exception");
            e.printStackTrace();
        }
        finally {
            FileSystemUtils.deleteRecursively(problemFolder);
        }
    }
}