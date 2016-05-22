package br.ufcg.edu.Detonator.command;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class FireTerminal extends CommandGene {

	private static final long serialVersionUID = -3534898622029777249L;
	
	public FireTerminal(GPConfiguration a_conf) throws InvalidConfigurationException {
		super(a_conf, 1, Void.class);
	}
	
	@Override
	public Class getChildType(IGPProgram a_ind, int a_chromNum) {
		return DoubleClass;
	}
	
	@Override
	public String toString() {
		return "fire(&1);";
	}

}
