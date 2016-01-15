package org.btrg.optaplannerhellotest;

import org.btrg.optaplannerhellotest.domain.HelloSolution;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

public class HelloApp {
    public static final String SOLVER_CONFIG_XML = "org/btrg/optaplannerhellotest/solver/helloCounterSolverConfig.xml";
    public static ScoreDirector scoreDirector;
    public static int printCount = 0;

    public static void main(String[] args) {
        /*  Crea un solver a partire dalla definizione XML.
            Il file dice che x.domain.HelloSolution contiene la SolutionClass,
            e x.domain.HelloEntity contiene l'EntityClass.
        
            La Score Directory Factory dice che lo score è HARD_SOFT, definito in helloCounterScoreRules.drl.
        
            Poi dice che termina quando ha fatto 10 secondi di calcolo.
        
            Il resto, boh.
        */
        
        Solver solver = SolverFactory.createFromXmlResource(SOLVER_CONFIG_XML).buildSolver();

        // Crea lo ScoreDirector a partire dalla Factory definita nel solver
        ScoreDirectorFactory scoreDirectorFactory = solver.getScoreDirectorFactory();
        scoreDirector = scoreDirectorFactory.buildScoreDirector();
        
        // x.domain.HelloSolution (è una SolutionClass)
        HelloSolution helloSolution = new HelloSolution();
        scoreDirector.setWorkingSolution(helloSolution);
        
        System.out.println("\n\nTRYING COUNT AT:");
        solver.solve(helloSolution);
        
        HelloSolution bestHelloSolution = (HelloSolution) solver.getBestSolution();
        System.out.println("\n\nBEST SOLUTION HAS EXACTLY " + bestHelloSolution.getTaskList().get(0).getCount() + " HELLOS");
    }

    public static String getSpacerFeed() {
        printCount++;
        if (printCount == 25) {
            printCount = 0;
            return "\n";
        } else {
            return " ";
        }
    }
}
