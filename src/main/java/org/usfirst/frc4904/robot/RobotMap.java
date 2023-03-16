package org.usfirst.frc4904.robot;

import org.usfirst.frc4904.standard.custom.controllers.CustomCommandJoystick;

import org.usfirst.frc4904.standard.custom.motorcontrollers.CANTalonFX;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.revrobotics.CANSparkMax.IdleMode;
import org.usfirst.frc4904.robot.subsystems.Intake;
import org.usfirst.frc4904.robot.subsystems.arm.ArmExtensionSubsystem;
import org.usfirst.frc4904.robot.subsystems.arm.ArmPivotSubsystem;
import org.usfirst.frc4904.robot.subsystems.arm.ArmSubsystem;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// import org.usfirst.frc4904.standard.LogKitten;

import org.usfirst.frc4904.standard.custom.motorcontrollers.CustomCANSparkMax;
import org.usfirst.frc4904.standard.subsystems.motor.SparkMaxMotorSubsystem;
import org.usfirst.frc4904.standard.subsystems.chassis.WestCoastDrive;
import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorSubsystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import org.usfirst.frc4904.standard.custom.sensors.NavX;
import org.usfirst.frc4904.robot.subsystems.Chassis2023;
import org.usfirst.frc4904.robot.subsystems.net.RobotUDP;

public class RobotMap {

    public static class Port {
        // public static class Network {
        //     public static SocketAddress LOCAL_SOCKET_ADDRESS = new InetSocketAddress(3375);
        //     public static SocketAddress LOCALIZATION_ADDRESS = new InetSocketAddress("10.49.04.10", 4321);
        // }

        public static class HumanInput {
            public static final int joystick = 0;
            public static final int xboxController = 1;
        }

        // // blinky constants
        // // TODO: go to 2023 robot constants for comp
        // public static class CANMotor {
        //     public static final int LEFT_DRIVE_A = 3;
        //     public static final int RIGHT_DRIVE_A = 2;
        //     public static final int LEFT_DRIVE_B = 5;
        //     public static final int RIGHT_DRIVE_B = 4;


        
        // 2023 robot constants
        public static class CANMotor {
            public static final int RIGHT_DRIVE_A = 3; // TODO: Check chassis motor IDs
            public static final int RIGHT_DRIVE_B = 4;
            public static final int LEFT_DRIVE_A = 1;
            public static final int LEFT_DRIVE_B = 2;

            public static final int PIVOT_MOTOR_LEFT = 11;
            public static final int PIVOT_MOTOR_RIGHT = 12;
            public static final int ARM_EXTENSION_MOTOR = 14;

            // public static final int LEFT_INTAKE = 21;
            // public static final int RIGHT_INTAKE = 22;
        }

        public static class PWM {
        }

        public static class CAN {
        }

        public static class Pneumatics {
        }

        public static class Digital {
        }

       }

    public static class Metrics {
        // blinky constants
        // TODO: go to 2023-robot constants for comp
        // public static class Chassis {
        //     public static final double GEAR_RATIO = 69/5;
        //     public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);
        //     public static final double TRACK_WIDTH_METERS = 0.59;
        // }

        // // 2023-robot constants
        public static class Chassis {
            public static final double GEAR_RATIO = 496/45; // https://www.desmos.com/calculator/llz7giggcf
            public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(5);
            public static final double TRACK_WIDTH_METERS = Units.inchesToMeters(19.5); // +/- 0.5 inches
        }
    }

    public static class PID {
        public static class Drive {
            // PID constants
            public static final double kP = 1.5;
            public static final double kI = 0;  // TODO: tune
            public static final double kD = 0;  // TODO: tune
            // feedforward constants
            // these are blinky constants from sysid on new drivetrain wednesday before 
            public static final double kS = 0.025236; 
            public static final double kV = 3.0683;
            public static final double kA = 0.7358;
        }

        public static class Turn {
        }
    }

    public static class Component {
        // expose these for robotcontainer2
        public static CANTalonFX frontLeftWheelTalon;
        public static CANTalonFX frontRightWheelTalon;
        public static CANTalonFX backLeftWheelTalon;
        public static CANTalonFX backRightWheelTalon;

        public static AHRS navx;

        // public static RobotUDP robotUDP;

        public static WestCoastDrive chassis;
        public static ArmSubsystem arm;
        public static Intake intake;
    }

    public static class NetworkTables {
        public static NetworkTableInstance instance;

        public static class Odometry {
            public static NetworkTable table;
            public static NetworkTableEntry pose;
            public static NetworkTableEntry accel;
            public static NetworkTableEntry turretAngle;
        }

        public static class Localization {
            public static NetworkTable table;
            public static NetworkTableEntry goalDistance;
            public static NetworkTableEntry goalRelativeAngle;
        }
    }

    public static class Input {
    }

    public static class HumanInput {
        public static class Driver {
            public static CommandXboxController xbox;
        }

        public static class Operator {
            public static CustomCommandJoystick joystick;
        }
    }

    public RobotMap() {
        Component.navx = new AHRS(SerialPort.Port.kMXP);

        HumanInput.Driver.xbox = new CommandXboxController(Port.HumanInput.xboxController);
		HumanInput.Operator.joystick = new CustomCommandJoystick(Port.HumanInput.joystick);
        // // UDP things
        // try {
        //     Component.robotUDP = new RobotUDP(Port.Network.LOCAL_SOCKET_ADDRESS, Port.Network.LOCALIZATION_ADDRESS);
        // } catch (IOException ex) {
        //     LogKitten.f("Failed to initialize UDP subsystem");
        //     LogKitten.ex(ex);
        // }


        /***********************
         * Chassis Subsystem
        *************************/

        Component.backRightWheelTalon  = new CANTalonFX(Port.CANMotor.RIGHT_DRIVE_A, InvertType.None);
        Component.frontRightWheelTalon = new CANTalonFX(Port.CANMotor.RIGHT_DRIVE_B, InvertType.FollowMaster);
        Component.backLeftWheelTalon   = new CANTalonFX(Port.CANMotor.LEFT_DRIVE_A, InvertType.None);
        Component.frontLeftWheelTalon  = new CANTalonFX(Port.CANMotor.LEFT_DRIVE_B, InvertType.FollowMaster);

        TalonMotorSubsystem leftDriveMotors  = new TalonMotorSubsystem("left drive motors",  NeutralMode.Brake, 0, Component.frontLeftWheelTalon, Component.backLeftWheelTalon);
        TalonMotorSubsystem rightDriveMotors = new TalonMotorSubsystem("right drive motors", NeutralMode.Brake, 0, Component.frontRightWheelTalon, Component.backRightWheelTalon);
        Component.chassis = new WestCoastDrive(
            Metrics.Chassis.TRACK_WIDTH_METERS, Metrics.Chassis.GEAR_RATIO, Metrics.Chassis.WHEEL_DIAMETER_METERS,
            PID.Drive.kP, PID.Drive.kI, PID.Drive.kD,
            Component.navx, leftDriveMotors, rightDriveMotors
        );

        Component.chassis.setDefaultCommand(Component.chassis.c_controlChassisSpeedAndTurn(() -> new Pair<Double, Double>(Robot.drivingConfig.getX(), Robot.drivingConfig.getTurnSpeed())));


        /***********************
         * Arm Subsystem
        *************************/

        CANTalonFX leftPivotMotor  = new CANTalonFX(RobotMap.Port.CANMotor.PIVOT_MOTOR_LEFT,  InvertType.InvertMotorOutput);
        CANTalonFX rightPivotMotor = new CANTalonFX(RobotMap.Port.CANMotor.PIVOT_MOTOR_RIGHT, InvertType.None);
        CANTalonFX armExtensionMotor = new CANTalonFX(Port.CANMotor.ARM_EXTENSION_MOTOR, InvertType.None);

        TalonMotorSubsystem armRotationMotors = new TalonMotorSubsystem("Arm Pivot Subsystem", NeutralMode.Brake, 0, leftPivotMotor, rightPivotMotor);
        ArmExtensionSubsystem armExtensionSubsystem = new ArmExtensionSubsystem(
            new TalonMotorSubsystem("Arm Extension Subsystem", NeutralMode.Brake, 0, armExtensionMotor),
            () -> ArmPivotSubsystem.motorRevsToAngle(armRotationMotors.getSensorPositionRotations())
        );
        // Autonomous.autonCommand = Component.chassis.c_buildPathPlannerAuto(
        //     PID.Drive.kS, PID.Drive.kV, PID.Drive.kA,
        //     Autonomous.RAMSETE_B, Autonomous.RAMSETE_ZETA,
        //     Autonomous.AUTON_NAME, Autonomous.MAX_VEL, Autonomous.MAX_ACCEL,
        //     Autonomous.autonEventMap
        // );

        ArmPivotSubsystem armPivotSubsystem = new ArmPivotSubsystem(armRotationMotors, armExtensionSubsystem::getCurrentExtensionLength);

        Component.arm = new ArmSubsystem(armPivotSubsystem, armExtensionSubsystem);

        armPivotSubsystem.initializeEncoderPositions();
        // TODO: rotate the arm up into frame-perimeter rest
        // TODO: armExtensionSubsystem.zeroSensors();

        /***********************
         * Intake Subsystem
        *************************/
        
        // CustomCANSparkMax intake_left = new CustomCANSparkMax(Port.CANMotor.LEFT_INTAKE, null, false);
        // CustomCANSparkMax intake_right = new CustomCANSparkMax(Port.CANMotor.RIGHT_INTAKE, null, true);
        // SparkMaxMotorSubsystem intake_motors = new SparkMaxMotorSubsystem("intake", IdleMode.kCoast, 0, intake_left, intake_right);
        // Component.intake = new Intake(intake_motors);

        // links we'll need
        // - angles and distances for intake/outtake: https://docs.google.com/spreadsheets/d/1B7Ie4efOpuZb4UQsk8lHycGvi6BspnF74DUMLmiKGUM/edit?usp=sharing
        // - naive + scuffed ramsete tuning: https://docs.google.com/spreadsheets/d/1BIvwJ6MfLf9ByW9dcmagXFvm7HPaXY78Y4YB1L9TGPA/edit#gid=0
    }
}