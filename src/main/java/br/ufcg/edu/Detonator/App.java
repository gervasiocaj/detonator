package br.ufcg.edu.Detonator;

import org.apache.log4j.BasicConfigurator;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import br.ufcg.edu.Detonator.command.FireTerminal;

public class App extends GPProblem {
	
    public App() throws InvalidConfigurationException {
        super(new GPConfiguration());

        GPConfiguration config = getGPConfiguration();

        config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        config.setMaxInitDepth(4);
        config.setPopulationSize(10);
        config.setMaxCrossoverDepth(8);
        config.setFitnessFunction(new MyFitnessFunction());
        //config.setStrictProgramCreation(true);
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
            	new Terminal(config, CommandGene.DoubleClass, 0d, 1d),
            	new Terminal(config, CommandGene.DoubleClass, 1d, 2d),
                new Add(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new FireTerminal(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
                new Add(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new FireTerminal(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
                new Add(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new FireTerminal(config),
            },
            {
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
            	new Terminal(config, CommandGene.DoubleClass),
                new Add(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new FireTerminal(config),
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
        gp.setVerboseOutput(true);
        gp.evolve(30);

        gp.outputSolution(gp.getAllTimeBest());
    }
}
