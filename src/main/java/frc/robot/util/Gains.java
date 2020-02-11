package frc.robot.util;

import org.team997coders.spartanlib.helpers.PIDConstants;

public class Gains {
	public final double kP;
	public final double kI;
	public final double kD;
	public final double kF;
	
	public Gains(double _kP, double _kI, double _kD, double _kF){
		kP = _kP;
		kI = _kI;
		kD = _kD;
		kF = _kF;
	}

	public PIDConstants toPIDConstants() {
		return new PIDConstants(kP, kI, kD);
	}
}