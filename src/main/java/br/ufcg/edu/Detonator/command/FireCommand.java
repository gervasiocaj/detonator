package br.ufcg.edu.Detonator.command;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class FireCommand extends CommandGene {

	public FireCommand(GPConfiguration a_conf) throws InvalidConfigurationException {
		super(a_conf, 1, Void.class);
	}
	
	@Override
	public Class getChildType(IGPProgram a_ind, int a_chromNum) {
		return DoubleClass;
	}
	
	@Override
	public Object execute_object(ProgramChromosome c, int n, Object[] args) {
		return "fire(" + c.execute_object(n, 0, args) + ");";
	}
	
	@Override
	public String toString() {
		return "fire(&1);";
	}

}
