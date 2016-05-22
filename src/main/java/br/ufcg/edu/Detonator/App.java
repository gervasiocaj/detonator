package br.ufcg.edu.Detonator;

import org.apache.log4j.BasicConfigurator;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;

import br.ufcg.edu.Detonator.command.AheadCommand;
import br.ufcg.edu.Detonator.command.BackCommand;
import br.ufcg.edu.Detonator.command.FireCommand;
import br.ufcg.edu.Detonator.command.MyAdd;
import br.ufcg.edu.Detonator.command.MyMultiply;
import br.ufcg.edu.Detonator.command.MySubProgram;

public class App extends GPProblem {
	
    public App() throws InvalidConfigurationException {
        super(new GPConfiguration());

        GPConfiguration config = getGPConfiguration();

        config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator());
        config.setMaxInitDepth(6);
        config.setPopulationSize(10);
        config.setMaxCrossoverDepth(6);
        config.setFitnessFunction(new MyFitnessFunction());
    }

    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration config = getGPConfiguration();

        Class[] types = {
        		CommandGene.VoidClass,
        		CommandGene.VoidClass,
        		CommandGene.VoidClass,
        		CommandGene.VoidClass,
		};

        Class[][] argTypes = {
        		{},
        		{},
        		{},
        		{},
		};

        CommandGene[][] nodeSets = {
            {
            	new MySubProgram(config, 2),
            	new MySubProgram(config, 3),
            	new MySubProgram(config, 4),
            	new MySubProgram(config, 5),

            	new Terminal(config, CommandGene.DoubleClass, 0d, 1d),
            	new Terminal(config, CommandGene.DoubleClass, 1d, 2d),
            	
            	// dimensions of the screen, mostly for ahead and back methods 
            	//new Terminal(config, CommandGene.DoubleClass, 0d, 800d),
            	//new Terminal(config, CommandGene.DoubleClass, 0d, 600d),
            	
            	new MyAdd(config),
                new MyMultiply(config),
                new FireCommand(config),
                new AheadCommand(config),
                new BackCommand(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass, 0d, 1d),
            	new Terminal(config, CommandGene.DoubleClass, 1d, 2d),
            	
            	new MyAdd(config),
                new MyMultiply(config),
                new FireCommand(config),
                new AheadCommand(config),
                new BackCommand(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass, 0d, 1d),
            	new Terminal(config, CommandGene.DoubleClass, 1d, 2d),
            	
            	new MyAdd(config),
                new MyMultiply(config),
                new FireCommand(config),
                new AheadCommand(config),
                new BackCommand(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass, 0d, 1d),
            	new Terminal(config, CommandGene.DoubleClass, 1d, 2d),
            	
            	new MyAdd(config),
                new MyMultiply(config),
                new FireCommand(config),
                new AheadCommand(config),
                new BackCommand(config),
            }
        };

        GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes,
                nodeSets, 20, true);

        return result;
    }

    public static void main(String[] args) throws Exception {
    	GPProblem problem = new App();

        BasicConfigurator.configure();
        
        GPGenotype gp = problem.create();
        gp.setVerboseOutput(false);
        gp.evolve(20);

        gp.outputSolution(gp.getAllTimeBest());
        MyFitnessFunction.createRobotFromChromossome(gp.getAllTimeBest());
    }
}
