package br.ufcg.edu.Detonator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

import javax.tools.*;

import org.jgap.gp.*;

import robocode.*;
import robocode.control.*;
import robocode.control.events.*;

public class MyFitnessFunction extends GPFitnessFunction {

	private static final long serialVersionUID = 5616535457545157300L;
	private static final String robotName = "ufcg.Detonator";
	protected double fitness;

	@Override
	protected double evaluate(IGPProgram a_subject) {
		final RobocodeEngine engine;
		final BattlefieldSpecification battlefield;
		createRobotFromChromossome(a_subject);

		engine = new RobocodeEngine(new File("C:/robocode"));
		engine.setVisible(false);
		engine.addBattleListener(new BattleAdaptor() {
			public void onBattleCompleted(final BattleCompletedEvent e) {
				double sum = 0;
				BattleResults[] list = e.getIndexedResults();
				for (BattleResults result : list) {
					sum += result.getScore();
					System.out.println("robot in battle: " + result.getTeamLeaderName());
				}

				System.out.println("sum: " + sum);
				for (BattleResults result : list) {
					if (result.getTeamLeaderName().startsWith(robotName)) {
						fitness = (double) result.getScore()/sum;
						
						System.out.println("async: " + fitness);
					}
				}
			}
		});
		battlefield = new BattlefieldSpecification(800, 600);
		final String robotsName = "sample.SpinBot, ufcg.Detonator*";
		final RobotSpecification[] selectedRobots = engine.getLocalRepository(robotsName);
		final BattleSpecification battleSpec = new BattleSpecification(10, battlefield, selectedRobots);
		engine.runBattle(battleSpec, true);
		engine.close();

		System.out.println("sync: " + fitness);
		return Math.abs(fitness);
	}

	private void createRobotFromChromossome(IGPProgram a_subject) {
		
		String sourceCode = "package ufcg;"
				+ "import robocode.*;"
				+ "import java.util.*;"
				+ "public class Detonator extends AdvancedRobot {"
				+ "	static double enemyFirePower;"
				+ "	public void run() {"
				+ "  while(true) {setTurnRight(10000);"
				+ "  setMaxVelocity(5);"
				+ "  ahead(10000);}"
				+ "	}"
				+ "	public void onScannedRobot(ScannedRobotEvent e) {"
				+ "		\n%s\n"
				+ "	}"
				+ "	public void onHitByBullet(HitByBulletEvent e) {"
				+ "		\n%s\n"
				+ "	}"
				+ "	public void onHitWall(HitWallEvent e) {"
				+ "		\n%s\n"
				+ "	}"
				+ "	public void onHitRobot(HitRobotEvent e) {"
				+ "		\n%s\n"
				+ "	}"
				+ "}";
		
		String scannedCode = "", bulletCode = "", wallCode = "", hitCode = "";
		
		scannedCode = a_subject.getChromosome(0).toStringNorm(0);
		System.out.println("codigo gerado: " + scannedCode);
		
		try {
			FileWriter fstream = new FileWriter("C:/robocode/robots/ufcg/Detonator.java");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(String.format(sourceCode, scannedCode, bulletCode, wallCode, hitCode));
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		DiagnosticListener<JavaFileObject> diagnosticListener = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, null, null);
		
		Iterable<String> options = Arrays.asList("-d", "C:/robocode/robots");
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects("C:/robocode/robots/ufcg/Detonator.java");
		
		boolean result = compiler.getTask(null, fileManager, diagnosticListener, options, null, compilationUnits).call();
		//Task(null, c, diagnosticListener, options, classes, compilationUnits)(null, null, null, "-cp", new File("robocode.jar").getAbsolutePath(), new File("robots/ufcg/Detonator.java").getAbsolutePath());
        if (result) {
            System.out.println("compilation done");
        } else {
        	System.err.println("compilation failed");
        }
	}

}
