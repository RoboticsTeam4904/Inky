/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc4904.robot;

// import java.util.function.BooleanSupplier;
// import java.util.function.DoubleSupplier;
//import java.util.function.Supplier;

import org.usfirst.frc4904.robot.humaninterface.drivers.SwerveGain;
import org.usfirst.frc4904.robot.humaninterface.operators.DefaultOperator;
import org.usfirst.frc4904.standard.CommandRobotBase;
//import org.usfirst.frc4904.standard.CommandRobotBase;
// import org.usfirst.frc4904.standard.custom.CommandSendableChooser;
import org.usfirst.frc4904.standard.humaninput.Driver;

import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.Kinematics;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

// import com.ctre.phoenix6.signals.NeutralModeValue;

// import edu.wpi.first.math.controller.DifferentialDriveWheelVoltages;

// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.button.Trigger;

import static org.usfirst.frc4904.robot.Utils.nameCommand;

import javax.swing.RowFilter.ComparisonType;

import org.usfirst.frc4904.robot.RobotMap.Component;

public class Robot extends CommandRobotBase {
    // private final RobotMap map = new RobotMap();
    // private final RobotContainer2 donttouchme = new RobotContainer2(RobotMap.Component.frontLeftWheelTalon, RobotMap.Component.backLeftWheelTalon, RobotMap.Component.frontRightWheelTalon, RobotMap.Component.backRightWheelTalon, RobotMap.Component.navx);
    // private SendableChooser<Supplier<Command>> autonomousCommand = new SendableChooser<Supplier<Command>>();

    private final Driver driver = new SwerveGain();
    private final org.usfirst.frc4904.standard.humaninput.Operator operator = new DefaultOperator();
    private final RobotMap map = new RobotMap();

    protected double scaleGain(double input, double gain, double exp) {
		return Math.pow(Math.abs(input), exp) * gain * Math.signum(input);
	}

    public Robot() {
        super();
        //can set deafault auton command here
        }

    @Override
    public void initialize() {
    }

    @Override
    public void teleopInitialize() {

        driver.bindCommands();
        operator.bindCommands();

        final double TURN_MULTIPLIER = 2;
        RobotMap.Component.chassis.setDefaultCommand(
            nameCommand("chassis - Teleop_Default - c_swerveDrive", 
                RobotMap.Component.chassis.c_drive(
                    () -> {
                        var target = new ChassisSpeeds(driver.getX(), driver.getY(), driver.getTurnSpeed());
                        SmartDashboard.putNumber("ChassisSpeeds x", target.vxMetersPerSecond);
                        SmartDashboard.putNumber("ChassisSpeeds y", target.vyMetersPerSecond);
                        SmartDashboard.putNumber("ChassisSpeeds omega", target.omegaRadiansPerSecond);
                        return target;
                    }, true)
                ));
    }

    @Override
    public void teleopExecute() {
        //various logging can go here
        //TODO: getAbsolutePosition() MIGHT NOT WORK OR BE IN RIGHT UNITS!
        SmartDashboard.putNumber("navxyaw", RobotMap.Component.navx.getYaw());
        
        SmartDashboard.putNumber("FL angle-1", RobotMap.Component.FLturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("FL angle-2 (currentyl using 2)", RobotMap.Component.FLmodule.getAbsoluteAngle());
        
        SmartDashboard.putNumber("FR angle-1", RobotMap.Component.FRturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("FR angle-2", RobotMap.Component.FRmodule.getAbsoluteAngle());
        SmartDashboard.putNumber("BL angle-1", RobotMap.Component.BLturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BL angle-2", RobotMap.Component.BLmodule.getAbsoluteAngle());
        SmartDashboard.putNumber("BR angle-1", RobotMap.Component.BRturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BR angle-2", RobotMap.Component.BRmodule.getAbsoluteAngle());

        var moduleTargets = RobotMap.Component.chassis.kinematics.toSwerveModuleStates(new ChassisSpeeds(driver.getX(), driver.getY(), driver.getTurnSpeed()));
        for (var c : moduleTargets){
            SmartDashboard.putNumber("placeholder", c.angle.getDegrees());
        }
        SmartDashboard.putNumber("FL speed", RobotMap.Component.FLdrive.get());
        SmartDashboard.putNumber("FR speed", RobotMap.Component.FRdrive.get());
        SmartDashboard.putNumber("BL speed", RobotMap.Component.BLdrive.get());
        SmartDashboard.putNumber("BR speed", RobotMap.Component.BRdrive.get());

        SmartDashboard.putNumber("driver X ", driver.getX());
        SmartDashboard.putNumber("driver Y ", driver.getY());
        SmartDashboard.putNumber("driver Z ", driver.getTurnSpeed());

        //navx gyro readings
        SmartDashboard.putNumber("navx angle", RobotMap.Component.navx.getAngle());
    }

    @Override
    public void autonomousInitialize() {
        //start autons here
    }

    @Override
    public void autonomousExecute() {
        //logging can go here
    }

    @Override
    public void disabledInitialize() {
        //do things like setting brake mode here
    }

    @Override
    public void disabledExecute() {
    }

    @Override
    public void testInitialize() {
        //do things like setting neutral or brake mode on the mechanism or wheels here
    }
    
    @Override
    public void testExecute() {
        //coast drive
        RobotMap.Component.FLdrive.stopMotor();
        RobotMap.Component.FRdrive.stopMotor();
        RobotMap.Component.BLdrive.stopMotor();
        RobotMap.Component.BRdrive.stopMotor();

        RobotMap.Component.FLdrive.setNeutralMode(NeutralModeValue.Coast);
        RobotMap.Component.FRdrive.setNeutralMode(NeutralModeValue.Coast);
        RobotMap.Component.BLdrive.setNeutralMode(NeutralModeValue.Coast);
        RobotMap.Component.BRdrive.setNeutralMode(NeutralModeValue.Coast);
        
        //coast turn
        RobotMap.Component.FLturn.neutralOutput();
        RobotMap.Component.FRturn.neutralOutput();
        RobotMap.Component.BLturn.neutralOutput();
        RobotMap.Component.BRturn.neutralOutput();

        RobotMap.Component.FLturn.setCoastOnNeutral();
        RobotMap.Component.FRturn.setCoastOnNeutral();
        RobotMap.Component.BLturn.setCoastOnNeutral();
        RobotMap.Component.BRturn.setCoastOnNeutral();

        //various logging can go here
        //TODO: getAbsolutePosition() MIGHT NOT WORK OR BE IN RIGHT UNITS!
        SmartDashboard.putNumber("FL angle-1", RobotMap.Component.FLturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("FL angle-2 (currentyl using 2)", RobotMap.Component.FLmodule.getAbsoluteAngle());
        
        SmartDashboard.putNumber("FR angle-1", RobotMap.Component.FRturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("FR angle-2", RobotMap.Component.FRmodule.getAbsoluteAngle());
        SmartDashboard.putNumber("BL angle-1", RobotMap.Component.BLturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BL angle-2", RobotMap.Component.BLmodule.getAbsoluteAngle());
        SmartDashboard.putNumber("BR angle-1", RobotMap.Component.BRturnEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("BR angle-2", RobotMap.Component.BRmodule.getAbsoluteAngle());

        var moduleTargets = RobotMap.Component.chassis.kinematics.toSwerveModuleStates(new ChassisSpeeds(driver.getX(), driver.getY(), driver.getTurnSpeed()));
        for (var c : moduleTargets){
            SmartDashboard.putNumber(c.toString(), c.angle.getDegrees());
        }
        SmartDashboard.putNumber("FL speed", RobotMap.Component.FLdrive.get());
        SmartDashboard.putNumber("FR speed", RobotMap.Component.FRdrive.get());
        SmartDashboard.putNumber("BL speed", RobotMap.Component.BLdrive.get());
        SmartDashboard.putNumber("BR speed", RobotMap.Component.BRdrive.get());

        SmartDashboard.putNumber("driver X ", driver.getX());
        SmartDashboard.putNumber("driver Y ", driver.getY());
        SmartDashboard.putNumber("driver Z ", driver.getTurnSpeed());

        //navx gyro readings
        SmartDashboard.putNumber("navx angle", RobotMap.Component.navx.getAngle());
    }

    @Override
    public void alwaysExecute() {
        // logging stuff can go here
    }
}




    
